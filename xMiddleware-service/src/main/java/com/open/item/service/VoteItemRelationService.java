package com.open.item.service;

import java.util.List;

import com.open.item.entity.relation.vote.VoteItemRelation;

public interface VoteItemRelationService {

    public void save(VoteItemRelation voteItemRelation);

    public void delVir(VoteItemRelation voteItemRelation);

    public List<VoteItemRelation> findByParentId(String parentId);

    public VoteItemRelation findById(String id);

    public void update(VoteItemRelation voteItemRelation);

}
