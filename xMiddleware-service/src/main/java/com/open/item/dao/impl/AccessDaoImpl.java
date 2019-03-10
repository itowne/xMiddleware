package com.open.item.dao.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.open.item.dao.AccessDao;
import com.open.item.entity.Access;

@Repository("accessDao")
public class AccessDaoImpl extends BaseSupportDao implements AccessDao {

    @SuppressWarnings("unchecked")
    @Override
    public int findAccessCountByArtId(String articleId) {
        DetachedCriteria criteria = DetachedCriteria.forClass(Access.class);
        criteria.add(Restrictions.eq("articleId", articleId));
        return countByDeCriteria(criteria).intValue();
    }

}
