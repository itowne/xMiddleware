package com.open.item.service;

import java.util.List;

import com.open.item.entity.Img;
import com.open.item.entity.Page;

public interface ImgService {

    public Page<Img> findImgPage(Integer start, Integer pagesize);

    public List<Img> findImgList();

    public Img findById(String id);

    public void save(Img img);

    public void update(Img img);

    public List<Img> findByMd5(String md5);

    public void delImg(Img img);
}
