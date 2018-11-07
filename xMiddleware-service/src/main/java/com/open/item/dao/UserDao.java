package com.open.item.dao;

import java.util.List;

import com.open.item.entity.Page;
import com.open.item.entity.User;

public interface UserDao extends BaseDao {

    public Page<User> findUserPage(Integer start, Integer pagesize);

    public List<User> findUserList();

    public User findById(String id);

    public User findByLoginName(String loginName);

}
