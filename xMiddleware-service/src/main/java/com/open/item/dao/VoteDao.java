package com.open.item.dao;

import java.util.List;

import com.open.item.entity.Page;
import com.open.item.entity.Vote;

public interface VoteDao extends BaseDao {

    public Page<Vote> findVotePage(Integer start, Integer pagesize);

    public Vote findById(String id);

    public List<Vote> findVoteList();

}
