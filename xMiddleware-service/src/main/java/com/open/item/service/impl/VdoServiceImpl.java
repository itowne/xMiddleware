package com.open.item.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.open.item.dao.VdoDao;
import com.open.item.entity.Page;
import com.open.item.entity.Vdo;
import com.open.item.service.VdoService;

@Service("vdoService")
public class VdoServiceImpl implements VdoService {

    @Resource(name = "vdoDao")
    private VdoDao vdoDao;

    @Transactional(readOnly = true)
    @Override
    public Page<Vdo> findVdoPage(Integer start, Integer pagesize) {
        return vdoDao.findVdoPage(start, pagesize);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Vdo> findVdoList() {
        return vdoDao.findVdoList();
    }

    @Transactional(readOnly = true)
    @Override
    public Vdo findById(String id) {
        return vdoDao.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Vdo> findByMd5(String md5) {
        return vdoDao.findByMd5(md5);
    }

    @Transactional
    @Override
    public void save(Vdo vdo) {
        vdoDao.save(vdo);
    }

    @Transactional
    @Override
    public void update(Vdo vdo) {
        vdoDao.update(vdo);
    }

    @Transactional
    @Override
    public void del(Vdo vdo) {
        vdoDao.delete(vdo);
    }

}
