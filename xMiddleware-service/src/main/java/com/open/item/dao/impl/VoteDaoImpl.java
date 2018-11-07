package com.open.item.dao.impl;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.open.item.dao.VoteDao;
import com.open.item.entity.Page;
import com.open.item.entity.Vote;

@Repository("voteDao")
public class VoteDaoImpl extends BaseSupportDao implements VoteDao {

    @Override
    public Page<Vote> findVotePage(Integer start, Integer pagesize) {
        DetachedCriteria criteria = DetachedCriteria.forClass(Vote.class);
        criteria.addOrder(Order.desc("createTime"));
        return this.findPageByCriteria(criteria, start, pagesize);
    }

    @Override
    public Vote findById(String id) {
        DetachedCriteria criteria = DetachedCriteria.forClass(Vote.class);
        criteria.add(Restrictions.eq("voteId", id));
        List<Vote> list = (List<Vote>) getHibernateTemplate().findByCriteria(criteria);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<Vote> findVoteList() {
        DetachedCriteria criteria = DetachedCriteria.forClass(Vote.class);
        List<Vote> list = (List<Vote>) getHibernateTemplate().findByCriteria(criteria);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list;
    }

}
