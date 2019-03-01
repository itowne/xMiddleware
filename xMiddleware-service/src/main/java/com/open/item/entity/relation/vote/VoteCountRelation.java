package com.open.item.entity.relation.vote;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

/**
 * 投票统计数关联关系
 * 
 * @author towne
 * @date Sep 24, 2018
 */
@Entity
@Table(name = "t_vote_count_rl")
@DynamicUpdate
public class VoteCountRelation implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -7679208037278325027L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "vote_cout_rl_id")
    private String voteCountRelationId;

    @Column(name = "vote_id")
    private String voteId;

    @Column(name = "vote_item_rl_id")
    private String voteItemRelationId;

    @Column(name = "article_id")
    private String articleId;

    @Column(name = "counter")
    private long counter;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getVoteCountRelationId() {
        return voteCountRelationId;
    }

    public void setVoteCountRelationId(String voteCountRelationId) {
        this.voteCountRelationId = voteCountRelationId;
    }

    public String getVoteId() {
        return voteId;
    }

    public void setVoteId(String voteId) {
        this.voteId = voteId;
    }

    public String getVoteItemRelationId() {
        return voteItemRelationId;
    }

    public void setVoteItemRelationId(String voteItemRelationId) {
        this.voteItemRelationId = voteItemRelationId;
    }

    public long getCounter() {
        return counter;
    }

    public void setCounter(long counter) {
        this.counter = counter;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }
}
