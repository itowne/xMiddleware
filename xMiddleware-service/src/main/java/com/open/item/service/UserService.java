package com.open.item.service;

import com.open.item.entity.Page;
import com.open.item.entity.User;

public interface UserService {

    public void save(User user);

    public Page<User> findUserPage(Integer start, Integer pagesize, String loginName);

    public User findById(String id);

    public void update(User user);

    public void deleteUser(User user);

    public User findByLoginName(String loginName);

}
