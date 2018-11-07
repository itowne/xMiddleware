package com.open.item.dao;

import java.util.List;

import com.open.item.entity.relation.vote.VoteItemRelation;

public interface VoteItemRelationDao extends BaseDao {

    public List<VoteItemRelation> findByParentId(String parentId);

    public VoteItemRelation findById(String id);

}
