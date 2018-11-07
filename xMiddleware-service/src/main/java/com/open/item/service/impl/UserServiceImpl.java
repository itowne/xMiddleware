package com.open.item.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.open.item.dao.UserDao;
import com.open.item.entity.Page;
import com.open.item.entity.User;
import com.open.item.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Resource(name = "userDao")
    private UserDao userDao;

    @Transactional
    @Override
    public void save(User user) {
        userDao.save(user);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<User> findUserPage(Integer start, Integer pagesize) {
        return userDao.findUserPage(start, pagesize);
    }

    @Transactional(readOnly = true)
    @Override
    public User findById(String id) {
        return userDao.findById(id);
    }

    @Transactional
    @Override
    public void update(User user) {
        user.setUpdTime(new Date());
        userDao.update(user);
    }

    @Transactional
    @Override
    public void deleteUser(User user) {
        userDao.delete(user);
    }

    @Transactional(readOnly = true)
    @Override
    public User findByLoginName(String loginName) {
        return userDao.findByLoginName(loginName);
    }

}
