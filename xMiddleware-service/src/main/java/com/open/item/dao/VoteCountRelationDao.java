package com.open.item.dao;

import java.util.List;

import com.open.item.entity.relation.vote.VoteCountRelation;

public interface VoteCountRelationDao extends BaseDao {

    public VoteCountRelation findByTripleId(String articleId, String voteId, String voteItemId);

    public List<VoteCountRelation> findByVoteIdAndArticleId(String articleId, String voteId);

    public VoteCountRelation findById(String id);
}
