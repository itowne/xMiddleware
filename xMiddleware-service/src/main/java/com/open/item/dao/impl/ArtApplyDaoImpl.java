package com.open.item.dao.impl;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.open.item.dao.ArtApplyDao;
import com.open.item.entity.ArtApply;
import com.open.item.entity.Page;

@Repository("artApplyDao")
public class ArtApplyDaoImpl extends BaseSupportDao implements ArtApplyDao {

    @SuppressWarnings("unchecked")
    @Override
    public List<ArtApply> findListByArtId(String artId) {
        DetachedCriteria criteria = DetachedCriteria.forClass(ArtApply.class);
        criteria.add(Restrictions.eq("articleId", artId));
        List<ArtApply> list = (List<ArtApply>) getHibernateTemplate().findByCriteria(criteria);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list;
    }

    @Override
    public Page<ArtApply> findArtApplyPage(Integer start, Integer pagesize, String articleId) {
        DetachedCriteria criteria = DetachedCriteria.forClass(ArtApply.class);
        criteria.add(Restrictions.eq("articleId", articleId));
        criteria.addOrder(Order.desc("createTime"));
        return this.findPageByCriteria(criteria, start, pagesize);
    }

    @Override
    public ArtApply findByArtcleIdAndMobile(String articleId, String mobile) {
        DetachedCriteria criteria = DetachedCriteria.forClass(ArtApply.class);
        criteria.add(Restrictions.eq("articleId", articleId));
        criteria.add(Restrictions.eq("mobile", mobile));
        List<ArtApply> list = (List<ArtApply>) getHibernateTemplate().findByCriteria(criteria);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }

}
