package com.open.item.service;

import java.util.List;

import com.open.item.entity.Page;
import com.open.item.entity.Vdo;

public interface VdoService {

    public Page<Vdo> findVdoPage(Integer start, Integer pagesize);

    public List<Vdo> findVdoList();

    public Vdo findById(String id);

    public List<Vdo> findByMd5(String md5);

    public void save(Vdo vdo);

    public void update(Vdo vdo);

    public void del(Vdo vdo);
}
