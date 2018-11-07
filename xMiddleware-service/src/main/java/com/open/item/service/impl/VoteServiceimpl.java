package com.open.item.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.open.item.dao.VoteDao;
import com.open.item.entity.Page;
import com.open.item.entity.Vote;
import com.open.item.service.VoteService;

@Service("voteService")
public class VoteServiceimpl implements VoteService {

    @Resource(name = "voteDao")
    private VoteDao voteDao;

    @Override
    @Transactional
    public void save(Vote user) {
        voteDao.save(user);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Vote> findVotePage(Integer start, Integer pagesize) {
        return voteDao.findVotePage(start, pagesize);
    }

    @Transactional(readOnly = true)
    @Override
    public Vote findById(String id) {
        return voteDao.findById(id);
    }

    @Transactional
    @Override
    public void update(Vote vote) {
        voteDao.update(vote);
    }

    @Transactional
    @Override
    public void delVote(Vote vote) {
        voteDao.delete(vote);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Vote> findVoteList() {
        return voteDao.findVoteList();
    }

}
