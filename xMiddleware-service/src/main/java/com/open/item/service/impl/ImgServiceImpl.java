package com.open.item.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.open.item.dao.ImgDao;
import com.open.item.entity.Img;
import com.open.item.entity.Page;
import com.open.item.service.ImgService;

@Service("imgService")
public class ImgServiceImpl implements ImgService {

    @Resource(name = "imgDao")
    private ImgDao imgDao;

    @Transactional(readOnly = true)
    @Override
    public Page<Img> findImgPage(Integer start, Integer pagesize) {
        return imgDao.findImgPage(start, pagesize);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Img> findImgList() {
        return imgDao.findImgList();
    }

    @Transactional(readOnly = true)
    @Override
    public Img findById(String id) {
        return imgDao.findById(id);
    }

    @Transactional
    @Override
    public void save(Img img) {
        imgDao.save(img);
    }

    @Transactional
    @Override
    public void update(Img img) {
        imgDao.update(img);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Img> findByMd5(String md5) {
        return imgDao.findByMd5(md5);
    }

    @Transactional
    @Override
    public void delImg(Img img) {
        imgDao.delete(img);
    }
}
