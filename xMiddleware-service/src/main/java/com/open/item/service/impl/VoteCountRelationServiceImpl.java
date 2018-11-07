package com.open.item.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.open.item.dao.VoteCountRelationDao;
import com.open.item.entity.relation.vote.VoteCountRelation;
import com.open.item.service.VoteCountRelationService;

@Service("voteCountRelationService")
public class VoteCountRelationServiceImpl implements VoteCountRelationService {

    @Resource(name = "voteCountRelationDao")
    private VoteCountRelationDao voteCountRelationDao;

    @Transactional
    @Override
    public void save(VoteCountRelation vcr) {
        voteCountRelationDao.save(vcr);
    }

    @Transactional
    @Override
    public void update(VoteCountRelation vcr) {
        voteCountRelationDao.update(vcr);
    }

    @Transactional(readOnly = true)
    @Override
    public VoteCountRelation findByTripleId(String articleId, String voteId, String voteItemId) {
        return voteCountRelationDao.findByTripleId(articleId, voteId, voteItemId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VoteCountRelation> findByVoteIdAndArticleId(String articleId, String voteId) {
        return voteCountRelationDao.findByVoteIdAndArticleId(articleId, voteId);
    }

    @Override
    @Transactional(readOnly = true)
    public VoteCountRelation findById(String id) {
        return voteCountRelationDao.findById(id);
    }

}
