package com.open.item.dao.impl;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.open.item.dao.VoteCountRelationDao;
import com.open.item.entity.relation.vote.VoteCountRelation;

@Repository("voteCountRelationDao")
public class VoteCountRelationDaoImpl extends BaseSupportDao implements VoteCountRelationDao {

    @Override
    public VoteCountRelation findByTripleId(String articleId, String voteId, String voteItemId) {
        DetachedCriteria criteria = DetachedCriteria.forClass(VoteCountRelation.class);
        criteria.add(Restrictions.eq("articleId", articleId));
        criteria.add(Restrictions.eq("voteId", voteId));
        criteria.add(Restrictions.eq("voteItemRelationId", voteItemId));
        List<VoteCountRelation> list = (List<VoteCountRelation>) getHibernateTemplate().findByCriteria(criteria);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<VoteCountRelation> findByVoteIdAndArticleId(String articleId, String voteId) {
        DetachedCriteria criteria = DetachedCriteria.forClass(VoteCountRelation.class);
        criteria.add(Restrictions.eq("articleId", articleId));
        criteria.add(Restrictions.eq("voteId", voteId));
        List<VoteCountRelation> list = (List<VoteCountRelation>) getHibernateTemplate().findByCriteria(criteria);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list;
    }

    @Override
    public VoteCountRelation findById(String id) {
        DetachedCriteria criteria = DetachedCriteria.forClass(VoteCountRelation.class);
        criteria.add(Restrictions.eq("voteCountRelationId", id));
        List<VoteCountRelation> list = (List<VoteCountRelation>) getHibernateTemplate().findByCriteria(criteria);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }

}
