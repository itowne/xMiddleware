package com.open.item.dao.impl;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.open.item.dao.UserDao;
import com.open.item.entity.Page;
import com.open.item.entity.User;

@Repository("userDao")
public class UserDaoImpl extends BaseSupportDao implements UserDao {

    @Override
    public Page<User> findUserPage(Integer start, Integer pagesize, String loginName) {
        DetachedCriteria criteria = DetachedCriteria.forClass(User.class);
        if (StringUtils.isNotBlank(loginName)) {
            criteria.add(Restrictions.eq("loginName", loginName));
        }
        criteria.addOrder(Order.desc("createTime"));
        return this.findPageByCriteria(criteria, start, pagesize);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<User> findUserList() {
        DetachedCriteria criteria = DetachedCriteria.forClass(User.class);
        return (List<User>) getHibernateTemplate().findByCriteria(criteria);
    }

    @Override
    public User findById(String id) {
        DetachedCriteria criteria = DetachedCriteria.forClass(User.class);
        criteria.add(Restrictions.eq("userId", id));
        List<User> list = (List<User>) getHibernateTemplate().findByCriteria(criteria);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public User findByLoginName(String loginName) {
        DetachedCriteria criteria = DetachedCriteria.forClass(User.class);
        criteria.add(Restrictions.eq("loginName", loginName));
        List<User> list = (List<User>) getHibernateTemplate().findByCriteria(criteria);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }

}
