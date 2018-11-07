package com.open.item.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.open.item.dao.VoteItemRelationDao;
import com.open.item.entity.relation.vote.VoteItemRelation;
import com.open.item.service.VoteItemRelationService;

@Service("voteItemRelationService")
public class VoteItemRelationServiceImpl implements VoteItemRelationService {

    @Resource(name = "voteItemRelationDao")
    private VoteItemRelationDao voteItemRelationDao;

    @Override
    @Transactional
    public void save(VoteItemRelation voteItemRelation) {
        voteItemRelationDao.save(voteItemRelation);
    }

    @Override
    @Transactional
    public void delVir(VoteItemRelation voteItemRelation) {
        voteItemRelationDao.delete(voteItemRelation);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VoteItemRelation> findByParentId(String parentId) {
        return voteItemRelationDao.findByParentId(parentId);
    }

    @Override
    @Transactional(readOnly = true)
    public VoteItemRelation findById(String id) {
        return voteItemRelationDao.findById(id);
    }

    @Override
    @Transactional
    public void update(VoteItemRelation voteItemRelation) {
        voteItemRelationDao.update(voteItemRelation);
    }

}
