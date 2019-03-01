package com.open.item.dao.impl;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.open.item.dao.VdoDao;
import com.open.item.entity.Page;
import com.open.item.entity.Vdo;

@Repository("vdoDao")
public class VdoDaoImpl extends BaseSupportDao implements VdoDao {

    @Override
    public Page<Vdo> findVdoPage(Integer start, Integer pagesize) {
        DetachedCriteria criteria = DetachedCriteria.forClass(Vdo.class);
        criteria.addOrder(Order.desc("createTime"));
        return this.findPageByCriteria(criteria, start, pagesize);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Vdo> findVdoList() {
        DetachedCriteria criteria = DetachedCriteria.forClass(Vdo.class);
        return (List<Vdo>) getHibernateTemplate().findByCriteria(criteria);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Vdo findById(String id) {
        DetachedCriteria criteria = DetachedCriteria.forClass(Vdo.class);
        criteria.add(Restrictions.eq("vdoId", id));
        List<Vdo> list = (List<Vdo>) getHibernateTemplate().findByCriteria(criteria);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Vdo> findByMd5(String md5) {
        DetachedCriteria criteria = DetachedCriteria.forClass(Vdo.class);
        criteria.add(Restrictions.eq("fileMd5", md5));
        List<Vdo> list = (List<Vdo>) getHibernateTemplate().findByCriteria(criteria);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list;
    }

}
