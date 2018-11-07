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

import com.open.item.entity.enumObject.ImgEnum;
import com.open.item.entity.enumObject.StatEnum;

@Entity
@Table(name = "t_img")
public class Img implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -8525897834806209688L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "img_id")
    private String imgId;

    @Column(name = "img_name")
    private String imgName;

    @Column(name = "img_url")
    private String imgUrl;

    @Column(name = "ori_filename")
    private String oriFileName;

    @Column(name = "sys_filename")
    private String sysFileName;

    @Column(name = "file_md5")
    private String fileMd5;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "stat")
    private StatEnum stat;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "img_type")
    private ImgEnum imgType;

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

    public String getImgId() {
        return imgId;
    }

    public void setImgId(String imgId) {
        this.imgId = imgId;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
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

    public ImgEnum getImgType() {
        return imgType;
    }

    public void setImgType(ImgEnum imgType) {
        this.imgType = imgType;
    }
}
