package com.open.item.dao;

import java.util.List;

import com.open.item.entity.Img;
import com.open.item.entity.Page;

public interface ImgDao extends BaseDao {

    public Page<Img> findImgPage(Integer start, Integer pagesize);

    public List<Img> findImgList();

    public Img findById(String id);

    public List<Img> findByMd5(String md5);
}
