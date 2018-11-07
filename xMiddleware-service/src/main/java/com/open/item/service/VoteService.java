package com.open.item.service;

import java.util.List;

import com.open.item.entity.Page;
import com.open.item.entity.Vote;

public interface VoteService {

    public void save(Vote user);

    public Page<Vote> findVotePage(Integer start, Integer pagesize);

    public Vote findById(String id);

    public void update(Vote vote);

    public void delVote(Vote vote);

    public List<Vote> findVoteList();
}
