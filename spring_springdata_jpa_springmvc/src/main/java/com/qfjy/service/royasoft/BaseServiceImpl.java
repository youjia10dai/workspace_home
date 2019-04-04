package com.qfjy.service.royasoft;

// 第一次看到静态导入
import static org.springframework.util.CollectionUtils.isEmpty;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Table;
import com.qfjy.dao.royasoft.BaseDao;

public class BaseServiceImpl<T, ID extends Serializable> implements BaseService<T, ID>{

	public static final int BATCHSIZE = 200;
	
	protected BaseDao<T, ID> basedao;
	
	@Override
	public T save(T t) {
		return basedao.save(t);
	}

	@Override
	public T update(T t, ID id) {
		//先从数据库中查找数据
		T tDb = basedao.findOne(id);
		//比较两个对象,如果t对象上属性不为null就将值更新到tDB对象上
		copyBeanNotNull2Bean(t, tDb);
		return save(tDb);
	}

    protected void copyBeanNotNull2Bean(Object databean, Object tobean) {
        PropertyDescriptor origDescriptors[] = PropertyUtils.getPropertyDescriptors(databean);
        for (int i = 0; i < origDescriptors.length; i++) {
            String name = origDescriptors[i].getName();
            if ("class".equals(name)) {
                continue;
            }
            if (PropertyUtils.isReadable(databean, name) && PropertyUtils.isWriteable(tobean, name)) {
                try {
                    Object value = PropertyUtils.getSimpleProperty(databean, name);
                    if (value != null) {
                        org.apache.commons.beanutils.BeanUtils.copyProperty(tobean, name, value);
                    }
                } catch (Exception e) {
                }
            }
        }
    }
	
	@Override
	@Transactional
	public void saveBatch(List<T> list) {
		if(!isEmpty(list)){
			int length = list.size();
			int page = length / 200;
			int mod = length % 200;
			int i = 0;
			for( ; i < page; i++){
				basedao.save(list.subList(i * BATCHSIZE, BATCHSIZE * (i + 1)));
				basedao.flush();
			}
			if(mod > 0){
				basedao.save(list.subList(BATCHSIZE * (i + 1), length - 1));	
				basedao.flush();
			}
		}
	}

	@Override
	public void delete(ID id) {
		basedao.delete(id);
	}

	@Override
	public void deleteEntity(T t) {
		basedao.delete(t);
	}

	@Override
	public void deleteByIds(List<ID> ids) {
		List<T> list = basedao.findAll(ids);
		deleteBatch(list);
	}

	@Override
	public void deleteBatch(List<T> list) {
		basedao.deleteInBatch(list);
	}

	@Override
	public T findOne(ID id) {
		return basedao.findOne(id);
	}

	@Override
	public List<T> findByIds(List<ID> ids) {
		return basedao.findAll(ids);
	}

	@Override
	public List<T> findAll(Table<String, Operator, Object> conditionTable) {
		return conditionTable == null || conditionTable.isEmpty() ? basedao.findAll() : basedao.findAll(buildCondition(conditionTable));
	}

	private Specification<T> buildCondition(final Table<String, Operator, Object> conditionTable) {
		//获取Predicate对象
//		conditionTable
		return new Specification<T>(){
			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> where = new ArrayList<Predicate>();
				//使用CriteriaBuilder 去创建条件和连接条件
				//创建条件和条件的连接条件 
				Multimap<Operator, Predicate> criteriacMap = buildCriteria(root, query, cb, conditionTable);
				Collection<Predicate> ac = criteriacMap.get(Operator.AND);
				if(ac != null && ac.size() > 0){
					where.add(cb.and(ac.toArray(new Predicate[0])));
				}
				Collection<Predicate> oc = criteriacMap.get(Operator.OR);
				if(oc != null && oc.size() > 0){
					where.add(cb.or(oc.toArray(new Predicate[0])));
				}
				return cb.or(where.toArray(new Predicate[0]));
			}
		};
	}

	private Multimap<Operator, Predicate> buildCriteria(Root<T> root, CriteriaQuery<?> query,
			CriteriaBuilder builder, Table<String, Operator, Object> conditionTable) {
		Multimap<Operator, Predicate> criteriacMap = ArrayListMultimap.create();

        for (Operator op : conditionTable.columnKeySet()) {
            Map<String, Object> keyVaule = conditionTable.column(op);
            for (String key : keyVaule.keySet()) {
                if (StringUtils.isNotEmpty(key)) {
                    String[] keys = StringUtils.split(key, ".");
                    Path path = root;
                    for (String k : keys) {
                        path = path.get(k);
                    }
                    Object value = keyVaule.get(key);
                    switch (op) {
                        case NE:
                            criteriacMap.put(Operator.AND, builder.notEqual(path, value));
                            break;
                        case EQ:
                            criteriacMap.put(Operator.AND, builder.equal(path, value));
                            break;
                        case LIKE:
                            criteriacMap.put(Operator.AND, builder.like(path.as(String.class), "%" + ObjectUtils.toString(value) + "%"));
                            break;
                        case LLIKE:
                            criteriacMap.put(Operator.AND, builder.like(path.as(String.class), "%" + ObjectUtils.toString(value)));
                            break;
                        case RLIKE:
                            criteriacMap.put(Operator.AND, builder.like(path.as(String.class), ObjectUtils.toString(value) + "%"));
                            break;
                        case GT:
                            criteriacMap.put(Operator.AND, builder.greaterThan(path, (Comparable) value));
                            break;
                        case LT:
                            criteriacMap.put(Operator.AND, builder.lessThan(path, (Comparable) value));
                            break;
                        case GTE:
                            criteriacMap.put(Operator.AND, builder.greaterThanOrEqualTo(path, (Comparable) value));
                            break;
                        case LTE:
                            criteriacMap.put(Operator.AND, builder.lessThanOrEqualTo(path, (Comparable) value));
                            break;
                        case IN:
                            CriteriaBuilder.In in = builder.in(path);
                            if (!(value instanceof Collection)) {
                                throw new RuntimeException("鍊煎繀椤绘槸闆嗗悎绫诲瀷");
                            }
                            Collection values = (Collection) value;
                            Iterator vi = values.iterator();
                            while (vi.hasNext()) {
                                in.value(vi.next());
                            }
                            criteriacMap.put(Operator.AND, in);
                            break;
                        case BETWEEN:
                            if (!(value instanceof Comparable[])) {
                                throw new RuntimeException("鍊煎繀椤绘槸鏁扮粍绫诲瀷");
                            }
                            Comparable[] valueArray = (Comparable[]) value;
                            criteriacMap.put(Operator.AND, builder.between(path, valueArray[0], valueArray[1]));
                            break;
                        case ORNE:
                            criteriacMap.put(Operator.OR, builder.notEqual(path, value));
                            break;
                        case OREQ:
                            criteriacMap.put(Operator.OR, builder.equal(path, value));
                            break;
                        case ORLIKE:
                            criteriacMap.put(Operator.OR, builder.like(path.as(String.class), "%" + ObjectUtils.toString(value) + "%"));
                            break;
                        case ORGT:
                            criteriacMap.put(Operator.OR, builder.greaterThan(path, (Comparable) value));
                            break;
                        case ORLT:
                            criteriacMap.put(Operator.OR, builder.lessThan(path, (Comparable) value));
                            break;
                        case ORGTE:
                            criteriacMap.put(Operator.OR, builder.greaterThanOrEqualTo(path, (Comparable) value));
                            break;
                        case ORLTE:
                            criteriacMap.put(Operator.OR, builder.lessThanOrEqualTo(path, (Comparable) value));
                            break;
                        case ORIN:
                            CriteriaBuilder.In oin = builder.in(path);
                            if (!(value instanceof Collection)) {
                                throw new RuntimeException("鍊煎繀椤绘槸闆嗗悎绫诲瀷");
                            }
                            Collection ovalues = (Collection) value;
                            Iterator ovi = ovalues.iterator();
                            while (ovi.hasNext()) {
                                oin.value(ovi.next());
                            }
                            criteriacMap.put(Operator.OR, oin);
                            break;
                        case ORBETWEEN:
                            if (!(value instanceof Comparable[])) {
                                throw new RuntimeException("鍊煎繀椤绘槸鏁扮粍绫诲瀷");
                            }
                            Comparable[] ovalueArray = (Comparable[]) value;
                            criteriacMap.put(Operator.OR, builder.between(path, ovalueArray[0], ovalueArray[1]));
                            break;
                        default:
                            break;
                    }
                }
            }
        }
        return criteriacMap;
	}
	
	@Override
	public List<T> findAll(Table<String, Operator, Object> conditionTable, Sort sort) {
		return conditionTable == null || conditionTable.isEmpty() ? basedao.findAll(sort) : basedao.findAll(buildCondition(conditionTable),sort);
	}

	@Override
	public Page<T> findPage(Table<String, Operator, Object> conditionTable, Pageable pageable) {
		return conditionTable == null || conditionTable.isEmpty() ? basedao.findAll(pageable) : basedao.findAll(buildCondition(conditionTable), pageable);
	}
	
	@Override
	public List<T> findAll() {
		return basedao.findAll();
	}

	@Override
	public Page<T> findPage(Specification<T> specification, Pageable pageable) {
		return specification == null ? basedao.findAll(pageable) : basedao .findAll(specification, pageable);
	}

	@Override
	public Page<T> findPageByProperty(String propertyName, Object propertyValue, Pageable pageable) {
		BusinessAssert.notEmpty(propertyName, "propertyName不能为空", "");
		basedao.findAll(propertySpec(propertyName, propertyValue), pageable);
		return null;
	}

	private Specification<T> propertySpec(final String propertyName, final Object propertyValue) {
		return new Specification<T>() {
			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<Predicate>();
                if (null != propertyValue) {
                    list.add(cb.equal(root.get(propertyName), propertyValue));
                } else {
                    list.add(root.get(propertyName).isNull());
                }
                return cb.and(list.toArray(new Predicate[list.size()]));
			}
		};
	}

	@Override
	public T findOneByProperty(String propertyName, Object propertyValue) {
		BusinessAssert.notEmpty(propertyName, "propertyName不能为空", "");
		return basedao.findOne(propertySpec(propertyName, propertyValue));
	}

	@Override
	public List<T> findListByProperty(String propertyName, Object propertyValue) {
		BusinessAssert.notEmpty(propertyName, "propertyName不能为空", "");
		return basedao.findAll(propertySpec(propertyName, propertyValue));
	}
	
	protected Pageable getPage(Integer page, Integer rows, Order order) {
		page = page == null ? 0 : page - 1;
		rows = rows == null ? 10 : rows;
		Sort sort = new Sort(order);
		return new PageRequest(page, rows, sort);
	}

    protected Pageable getNativeSqlPage(Integer page, Integer rows) {
        page = page == null ? 0 : page - 1;
        rows = rows == null ? 10 : rows;
        return new PageRequest(page, rows);
    }
	
    protected Pageable getPage(Integer page, Integer rows, List<Order> orders) {
        if (CollectionUtils.isEmpty(orders)) {
            return getNativeSqlPage(page, rows);
        } else {
            page = page == null ? 0 : page - 1;
            rows = rows == null ? 10 : rows;
            Sort sort = new Sort(orders);
            return new PageRequest(page, rows, sort);
        }
    }
    
}
