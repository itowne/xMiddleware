package com.open.item.entity.relation;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.open.item.entity.enumObject.ActivityTypeEnum;

/**
 * 活动关联关系
 * 
 * @author towne
 * @date Sep 24, 2018
 */
@Entity
@Table(name = "t_act_rl")
public class ActivityRelation implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -5886742326208135389L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "act_relation_id")
    private String activityRelationId;

    @Column(name = "activity_id")
    private String activityId;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "activity_type")
    private ActivityTypeEnum activityTypeEnum;

    @Column(name = "activity_type_id")
    private String activityTypeId;

    @Column(name = "idx")
    private int idx;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getActivityRelationId() {
        return activityRelationId;
    }

    public void setActivityRelationId(String activityRelationId) {
        this.activityRelationId = activityRelationId;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public ActivityTypeEnum getActivityTypeEnum() {
        return activityTypeEnum;
    }

    public void setActivityTypeEnum(ActivityTypeEnum activityTypeEnum) {
        this.activityTypeEnum = activityTypeEnum;
    }

    public String getActivityTypeId() {
        return activityTypeId;
    }

    public void setActivityTypeId(String activityTypeId) {
        this.activityTypeId = activityTypeId;
    }

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }
}
