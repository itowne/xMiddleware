package com.open.item.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.open.item.dao.AccessDao;
import com.open.item.service.AccessService;

@Service("accessService")
public class AccessServiceImpl implements AccessService {

    @Resource(name = "accessDao")
    private AccessDao accessDao;
}
