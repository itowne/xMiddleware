package com.open.item.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import com.open.item.entity.enumObject.StatEnum;

@Entity
@Table(name = "t_vdo")
@DynamicUpdate
public class Vdo implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 6890224341613848020L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "vdo_id")
    private String vdoId;

    @Column(name = "vdo_name")
    private String vdoName;

    @Column(name = "vdo_url")
    private String vdoUrl;

    @Column(name = "ori_filename")
    private String oriFileName;

    @Column(name = "sys_filename")
    private String sysFileName;

    @Column(name = "file_md5")
    private String fileMd5;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "stat")
    private StatEnum stat;

    @Column(name = "create_id")
    private String createId;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "upd_time")
    private Date updTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getVdoId() {
        return vdoId;
    }

    public void setVdoId(String vdoId) {
        this.vdoId = vdoId;
    }

    public String getVdoName() {
        return vdoName;
    }

    public void setVdoName(String vdoName) {
        this.vdoName = vdoName;
    }

    public String getVdoUrl() {
        return vdoUrl;
    }

    public void setVdoUrl(String vdoUrl) {
        this.vdoUrl = vdoUrl;
    }

    public String getOriFileName() {
        return oriFileName;
    }

    public void setOriFileName(String oriFileName) {
        this.oriFileName = oriFileName;
    }

    public String getSysFileName() {
        return sysFileName;
    }

    public void setSysFileName(String sysFileName) {
        this.sysFileName = sysFileName;
    }

    public String getFileMd5() {
        return fileMd5;
    }

    public void setFileMd5(String fileMd5) {
        this.fileMd5 = fileMd5;
    }

    public StatEnum getStat() {
        return stat;
    }

    public void setStat(StatEnum stat) {
        this.stat = stat;
    }

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdTime() {
        return updTime;
    }

    public void setUpdTime(Date updTime) {
        this.updTime = updTime;
    }

    public String getCreateTimeLabel() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(getCreateTime());
    }

}
