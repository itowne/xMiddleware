package com.open.item.dao;

import java.util.List;

import com.open.item.entity.Page;
import com.open.item.entity.Vdo;

public interface VdoDao extends BaseDao {

    public Page<Vdo> findVdoPage(Integer start, Integer pagesize);

    public List<Vdo> findVdoList();

    public Vdo findById(String id);

    public List<Vdo> findByMd5(String md5);
}
