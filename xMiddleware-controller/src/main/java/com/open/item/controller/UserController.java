package com.open.item.controller;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.open.item.entity.User;
import com.open.item.entity.enumObject.StatEnum;
import com.open.item.entity.enumObject.UserRoleEnum;
import com.open.item.service.UserService;
import com.open.item.utils.IdWorkerUtils;

/**
 * 用户controller
 * 
 * @author towne
 * @date Sep 25, 2018
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Resource(name = "userService")
    private UserService userService;

    @ResponseBody
    @RequestMapping(value = "/addUser")
    public String addUser(HttpServletRequest request) {
        User u = new User();
        u.setLoginName("xxx");
        u.setMobile("13900000000");
        u.setPwd("111111");
        u.setUserId(IdWorkerUtils.usrIdWorker());
        u.setUserRole(UserRoleEnum.ADMIN);
        u.setCreateTime(new Date());
        u.setUserStat(StatEnum.VALID);
        u.setUpdTime(new Date());
        userService.save(u);
        return JSON.toJSONString(u);
    }
}
