package com.royasoft.util;
import java.util.Collection;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.google.common.collect.Lists;

/**
 * 构建JPA动态查询 <br>
 * 
 * @author jxue
 */
public class DynamicSpecifications {

    public static <T> Specification<T> bySearchFilter(final Collection<SearchFilter> filters, final Class<T> entityClazz) {
        return new Specification<T>() {

            @SuppressWarnings({"unchecked", "rawtypes"})
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                if ((filters != null) && !(filters.isEmpty())) {

                    List<Predicate> predicates = Lists.newArrayList();// 用于放and
                    List<Predicate> OrPredicates = Lists.newArrayList();// 用于放or

                    for (SearchFilter filter : filters) {
                        // nested path translate, 如Task的名为"user.name"的filedName, 转换为Task.user.name属性
                        String[] names = filter.fieldName.split(".");
                        Path expression = null;
                        if (names.length != 0)
                            expression = root.get(names[0]);
                        else
                            expression = root.get(filter.fieldName);
                        for (int i = 1; i < names.length; i++) {
                            expression = expression.get(names[i]);
                        }

                        // logic operator
                        switch (filter.operator) {
                            case NE:
                                predicates.add(builder.notEqual(expression, (Comparable) filter.value));
                                break;
                            case EQ:
                                predicates.add(builder.equal(expression, (Comparable) filter.value));
                                break;
                            case LIKE:
                                predicates.add(builder.like(expression, "%" + filter.value + "%"));
                                break;
                            case LLIKE:
                                predicates.add(builder.like(expression, filter.value + "%"));
                                break;
                            case RLIKE:
                                predicates.add(builder.like(expression, "%" + filter.value));
                                break;
                            case GT:
                                predicates.add(builder.greaterThan(expression, (Comparable) filter.value));
                                break;
                            case LT:
                                predicates.add(builder.lessThan(expression, (Comparable) filter.value));
                                break;
                            case GTE:
                                predicates.add(builder.greaterThanOrEqualTo(expression, (Comparable) filter.value));
                                break;
                            case LTE:
                                predicates.add(builder.lessThanOrEqualTo(expression, (Comparable) filter.value));
                                break;
                            case IN:
                                In in = builder.in(expression);
                                String[] a = filter.value.toString().split(",");
                                boolean b = true;
                                for (int i = 0; i < a.length; i++) {
                                    try {// 尝试转换成Long类型 jpa不会自动将String 转为Long 会报错
                                        Long.valueOf(a[i]);
                                    } catch (Exception e) {
                                        b = false;
                                        break;
                                    }
                                }
                                for (int i = 0; i < a.length; i++) {
                                    if (b)
                                        in.value(Long.valueOf(a[i]));
                                    else
                                        in.value(a[i]);
                                }

                                predicates.add(in);
                                break;
                            case ORNE:
                                OrPredicates.add(builder.notEqual(expression, (Comparable) filter.value));
                                break;
                            case OREQ:
                                OrPredicates.add(builder.equal(expression, (Comparable) filter.value));
                                break;
                            case ORLIKE:
                                OrPredicates.add(builder.like(expression, "%" + filter.value + "%"));
                                break;
                            case ORGT:
                                OrPredicates.add(builder.greaterThan(expression, (Comparable) filter.value));
                                break;
                            case ORLT:
                                OrPredicates.add(builder.lessThan(expression, (Comparable) filter.value));
                                break;
                            case ORGTE:
                                OrPredicates.add(builder.greaterThanOrEqualTo(expression, (Comparable) filter.value));
                                break;
                            case ORLTE:
                                OrPredicates.add(builder.lessThanOrEqualTo(expression, (Comparable) filter.value));
                                break;
                            case ORIN:
                                In orin = builder.in(expression);
                                String[] str = filter.value.toString().split(",");

                                boolean boo = true;
                                for (int i = 0; i < str.length; i++) {
                                    try {// 尝试转换成Long类型 jpa不会自动将String 转为Long 会报错
                                        Long.valueOf(str[i]);
                                    } catch (Exception e) {
                                        boo = false;
                                        break;
                                    }
                                }
                                for (int i = 0; i < str.length; i++) {
                                    if (boo)
                                        orin.value(Long.valueOf(str[i]));
                                    else
                                        orin.value(str[i]);
                                }

                                OrPredicates.add(orin);
                                break;
                            default:
                                break;
                        }
                    }
                    if (!predicates.isEmpty() && !OrPredicates.isEmpty()) {
                        List<Predicate> where = Lists.newArrayList();
                        where.add(builder.and(predicates.toArray(new Predicate[predicates.size()])));
                        where.add(builder.or(OrPredicates.toArray(new Predicate[OrPredicates.size()])));
                        return builder.and(where.toArray(new Predicate[where.size()]));
                    }

                    // 将所有条件用 and 联合起来
                    if (!predicates.isEmpty()) {
                        return builder.and(predicates.toArray(new Predicate[predicates.size()]));
                    }
                    if (!OrPredicates.isEmpty()) {
                        return builder.or(OrPredicates.toArray(new Predicate[OrPredicates.size()]));
                    }
                }

                return builder.conjunction();
            }
        };
    }
}