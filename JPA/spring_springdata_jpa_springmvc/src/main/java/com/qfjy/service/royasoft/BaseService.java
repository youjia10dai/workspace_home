package com.qfjy.service.royasoft;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import com.google.common.collect.Table;

public interface BaseService<T, ID> {

    T save(T t);

    T update(T t, ID id);

    /**
     * 批量更新,使用了JpaRepository中的批量的更新,并且每次更新200条
     * @param list
     */
    void saveBatch(List<T> list);

    void delete(ID id);

    void deleteEntity(T t);

    void deleteByIds(List<ID> ids);

    void deleteBatch(List<T> list);

    T findOne(ID id);

    List<T> findByIds(List<ID> ids);

    List<T> findAll(Table<String, Operator, Object> conditionTable);

    List<T> findAll(Table<String, Operator, Object> conditionTable, Sort sort);

    List<T> findAll();

    Page<T> findPage(Table<String, Operator, Object> conditionTable, Pageable pageable);

    Page<T> findPage(Specification<T> specification, Pageable pageable);

    Page<T> findPageByProperty(String propertyName, Object propertyValue, Pageable pageable);

    T findOneByProperty(String propertyName, Object propertyValue);

    List<T> findListByProperty(String propertyName, Object propertyValue);
}
