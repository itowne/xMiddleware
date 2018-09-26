package com.open.item.dao;

import java.io.Serializable;
import java.util.List;

/**
 * 基础Dao接口
 *
 * @author towne
 * @version 1.0.0 @ 20161201
 */
public interface BaseDao {

    /**
     * 根据主键查找对象
     *
     * @param obj
     * @param pk
     * @return
     */
    public <T> T findById(Class<T> entityClass, Serializable id);

    /**
     * 保存对象
     *
     * @param <T>
     * @param obj
     */
    public <T> T save(T entity);

    /**
     * 更新对象
     *
     * @param entity
     * @return
     */
    public <T> T update(T entity);

    /**
     * 删除对象
     *
     * @param entity
     */
    public <T> void delete(T entity);

    /**
     * 根据主键hibernate自行判断保存或是更新
     *
     * @param <T>
     * @param entity
     */
    public <T> void saveOrUpdate(T entity);

    /**
     * 查询所有记录
     *
     * @param entityClass
     * @return
     */
    public <T> List<T> findAll(Class<T> entityClass);
}
