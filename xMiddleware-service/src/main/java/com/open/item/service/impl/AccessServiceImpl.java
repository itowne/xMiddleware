package com.open.item.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.open.item.dao.AccessDao;
import com.open.item.entity.Access;
import com.open.item.service.AccessService;

@Service("accessService")
public class AccessServiceImpl implements AccessService {

    @Resource(name = "accessDao")
    private AccessDao accessDao;

    @Override
    @Transactional(readOnly = true)
    public int findAccessCountByArtId(String articleId) {
        return accessDao.findAccessCountByArtId(articleId);
    }

    @Override
    @Transactional
    public void save(Access access) {
        accessDao.save(access);
    }

    @Override
    @Transactional
    public void update(Access access) {
        accessDao.update(access);
    }
}
