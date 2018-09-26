/**
 *
 */
package com.open.item.dao.impl;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.internal.CriteriaImpl;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.orm.hibernate5.SessionHolder;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.open.item.dao.BaseDao;
import com.open.item.entity.Page;

/**
 * 基础DAO实现类
 *
 * @author towne
 * @version 1.0.0 @ 20161201
 */
@Repository
public class BaseSupportDao extends HibernateDaoSupport implements BaseDao {

    @Resource(name = "sessionFactory")
    public void injectSessionFactory(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }

    /**
     * 根据主键查找对象
     *
     * @param obj
     * @param pk
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> T findById(Class<T> entityClass, Serializable id) {
        return (T) getSessionFactory().getCurrentSession().get(entityClass, id);
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> findAll(Class<T> entityClass) {
        DetachedCriteria criteria = DetachedCriteria.forClass(entityClass);
        return (List<T>) getHibernateTemplate().findByCriteria(criteria);
    }

    /**
     * 保存对象
     *
     * @param <T>
     * @param obj
     */
    public <T> T save(T entity) {
        getSessionFactory().getCurrentSession().save(entity);
        return entity;
    }

    /**
     * 更新对象
     *
     * @param entity
     * @return
     */
    public <T> T update(T entity) {
        getSessionFactory().getCurrentSession().update(entity);
        return entity;
    }

    /**
     * 删除对象
     *
     * @param entity
     */
    public <T> void delete(T entity) {
        getSessionFactory().getCurrentSession().delete(entity);
    }

    @Override
    public <T> void saveOrUpdate(T entity) {
        getSessionFactory().getCurrentSession().saveOrUpdate(entity);
    }

    /**
     * 执行HQL语句，限用于批量修改/删除操作。
     *
     * @param hql
     * @param values
     * @return 返回删除/更新记录数
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public int excute(final String hql, final Object... values) {
        return (Integer) getHibernateTemplate().executeWithNativeSession(new HibernateCallback() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException {
                Query query = createQuery(session, hql, values);
                return query.executeUpdate();
            }
        });

    }

    public Query createQuery(Session session, String hql, Object... values) {
        Query query = session.createQuery(hql);
        if (getSessionFactory() != null) {
            SessionHolder sessionHolder = (SessionHolder) TransactionSynchronizationManager
                    .getResource(getSessionFactory());
            if (sessionHolder != null && sessionHolder.hasTimeout()) {
                query.setTimeout(sessionHolder.getTimeToLiveInSeconds());
            }
        }
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                query.setParameter(i, values[i]);
            }
        }
        return query;
    }

    /**
     * 根据DetachedCriteria执行查询，返回Page对象
     *
     * @param dc
     * @param startIndex
     * @param pageSize
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> Page<T> findPageByCriteria(DetachedCriteria dc, int startIndex, int pageSize) {
        int totalCount = this.countByDeCriteria(dc).intValue();
        Page<T> page = new Page<T>(null, totalCount, startIndex, pageSize);
        // dc.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        List<T> list = (List<T>) getHibernateTemplate().findByCriteria(dc, startIndex, pageSize);
        page.setItems(list);
        return page;
    }

    /**
     * 使用DetachedCriteria方式, 获取查询记录总数
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public Long countByDeCriteria(final DetachedCriteria dc) {
        return (Long) getHibernateTemplate().executeWithNativeSession(new HibernateCallback() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException {
                Criteria criteria = dc.getExecutableCriteria(session);
                CriteriaImpl criteriaImpl = (CriteriaImpl) criteria;
                Projection projection = criteriaImpl.getProjection();
                Long totalCount = (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
                if (totalCount == null) {
                    totalCount = 0L;
                }
                criteria.setProjection(projection);
                return totalCount;
            }
        });
    }

    /**
     * 根据DetachedCriteria执行查询，返回list
     *
     * @param criteria
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> findByCriteria(DetachedCriteria criteria) {
        // criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return (List<T>) this.getHibernateTemplate().findByCriteria(criteria);
    }

}
