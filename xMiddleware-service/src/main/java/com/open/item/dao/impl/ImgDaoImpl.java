package com.open.item.dao.impl;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.open.item.dao.ImgDao;
import com.open.item.entity.Img;
import com.open.item.entity.Page;
import com.open.item.entity.enumObject.ImgEnum;

@Repository("imgDao")
public class ImgDaoImpl extends BaseSupportDao implements ImgDao {

    @Override
    public Page<Img> findImgPage(Integer start, Integer pagesize) {
        DetachedCriteria criteria = DetachedCriteria.forClass(Img.class);
        criteria.add(Restrictions.eq("imgType", ImgEnum.IMG_IMG));
        criteria.addOrder(Order.desc("createTime"));
        return this.findPageByCriteria(criteria, start, pagesize);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Img> findImgList() {
        DetachedCriteria criteria = DetachedCriteria.forClass(Img.class);
        criteria.add(Restrictions.eq("imgType", ImgEnum.IMG_IMG));
        return (List<Img>) getHibernateTemplate().findByCriteria(criteria);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Img findById(String id) {
        DetachedCriteria criteria = DetachedCriteria.forClass(Img.class);
        criteria.add(Restrictions.eq("imgId", id));
        List<Img> list = (List<Img>) getHibernateTemplate().findByCriteria(criteria);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Img> findByMd5(String md5) {
        DetachedCriteria criteria = DetachedCriteria.forClass(Img.class);
        criteria.add(Restrictions.eq("fileMd5", md5));
        List<Img> list = (List<Img>) getHibernateTemplate().findByCriteria(criteria);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list;
    }

}
