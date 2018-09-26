package com.open.item.entity.relation.vote;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 投票关联关系
 * 
 * @author towne
 * @date Sep 24, 2018
 */
@Entity
@Table(name = "t_vote_rl")
public class VoteItemRelation implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 266992060264114326L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "vote_item_rl_id")
    private String voteItemRelationId;

    @Column(name = "vote_id")
    private String voteId;

    @Column(name = "vote_name")
    private String voteName;

    @Column(name = "idx")
    private int idx;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getVoteItemRelationId() {
        return voteItemRelationId;
    }

    public void setVoteItemRelationId(String voteItemRelationId) {
        this.voteItemRelationId = voteItemRelationId;
    }

    public String getVoteId() {
        return voteId;
    }

    public void setVoteId(String voteId) {
        this.voteId = voteId;
    }

    public String getVoteName() {
        return voteName;
    }

    public void setVoteName(String voteName) {
        this.voteName = voteName;
    }

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

}
