package com.open.item.service;

import java.util.List;

import com.open.item.entity.relation.vote.VoteCountRelation;

public interface VoteCountRelationService {

    public void save(VoteCountRelation vcr);

    public void update(VoteCountRelation vcr);

    public VoteCountRelation findByTripleId(String articleId, String voteId, String voteItemId);

    public List<VoteCountRelation> findByVoteIdAndArticleId(String articleId, String voteId);

    public VoteCountRelation findById(String id);
}
