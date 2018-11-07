package com.open.item.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.open.item.dao.ArticleDao;
import com.open.item.entity.Article;
import com.open.item.entity.Page;
import com.open.item.entity.enumObject.ViewTypeEnum;

@Repository("articleDao")
public class ArticleDaoImpl extends BaseSupportDao implements ArticleDao {

    @Override
    public Page<Article> findArtPage(Integer start, Integer pagesize) {
        DetachedCriteria criteria = DetachedCriteria.forClass(Article.class);
        criteria.addOrder(Order.desc("createTime"));
        return this.findPageByCriteria(criteria, start, pagesize);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Article> findArtList() {
        DetachedCriteria criteria = DetachedCriteria.forClass(Article.class);
        return (List<Article>) getHibernateTemplate().findByCriteria(criteria);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Article findById(String id) {
        DetachedCriteria criteria = DetachedCriteria.forClass(Article.class);
        criteria.add(Restrictions.eq("articleId", id));
        List<Article> list = (List<Article>) getHibernateTemplate().findByCriteria(criteria);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Article> findByType(ViewTypeEnum vte) {
        if (vte == null) {
            return new ArrayList<Article>();
        }
        DetachedCriteria criteria = DetachedCriteria.forClass(Article.class);
        Date cur = new Date();
        switch (vte) {
        case NOTICE:
            criteria.add(Restrictions.gt("startTime", cur));
            criteria.add(Restrictions.gt("endTime", cur));
            break;
        case CURRENT:
            criteria.add(Restrictions.le("startTime", cur));
            criteria.add(Restrictions.ge("endTime", cur));
            break;
        case HISTORY:
            criteria.add(Restrictions.lt("startTime", cur));
            criteria.add(Restrictions.lt("endTime", cur));
            break;
        default:
            return new ArrayList<Article>();
        }
        criteria.addOrder(Order.desc("createTime"));
        List<Article> list = (List<Article>) getHibernateTemplate().findByCriteria(criteria);
        if (CollectionUtils.isEmpty(list)) {
            return new ArrayList<Article>();
        }
        return list;
    }

}
