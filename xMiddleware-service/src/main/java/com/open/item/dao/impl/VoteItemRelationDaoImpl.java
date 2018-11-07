package com.open.item.dao.impl;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.open.item.dao.VoteItemRelationDao;
import com.open.item.entity.relation.vote.VoteItemRelation;

@Repository("voteItemRelationDao")
public class VoteItemRelationDaoImpl extends BaseSupportDao implements VoteItemRelationDao {

    @Override
    public List<VoteItemRelation> findByParentId(String parentId) {
        DetachedCriteria criteria = DetachedCriteria.forClass(VoteItemRelation.class);
        criteria.add(Restrictions.eq("voteId", parentId));
        List<VoteItemRelation> list = (List<VoteItemRelation>) getHibernateTemplate().findByCriteria(criteria);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list;
    }

    @Override
    public VoteItemRelation findById(String id) {
        DetachedCriteria criteria = DetachedCriteria.forClass(VoteItemRelation.class);
        criteria.add(Restrictions.eq("voteItemRelationId", id));
        List<VoteItemRelation> list = (List<VoteItemRelation>) getHibernateTemplate().findByCriteria(criteria);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }

}
