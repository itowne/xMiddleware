package com.open.item.controller;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.open.item.beans.Const;
import com.open.item.entity.User;
import com.open.item.entity.enumObject.UserRoleEnum;
import com.open.item.service.UserService;
import com.open.item.utils.CommonJson;
import com.open.item.utils.IdWorkerUtils;
import com.open.item.utils.PwdUtils;

/**
 * login controller
 * 
 * @author towne
 * @date Oct 18, 2018
 */
@Controller
public class LoginController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Resource(name = "userService")
    private UserService userService;

    @RequestMapping("/login")
    public ModelAndView toLogin() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("login");
        return mav;
    }

    @ResponseBody
    @RequestMapping("/doLogin")
    public String doLogin(HttpServletRequest request) {
        String loginName = request.getParameter("loginName");
        String pwd = request.getParameter("pwd");
        if (StringUtils.isBlank(loginName)) {
            return CommonJson.dataResponse(CommonJson.ERROR, "登录名不能为空");
        }
        if (StringUtils.isBlank(pwd)) {
            return CommonJson.dataResponse(CommonJson.ERROR, "密码不能为空");
        }
        User exUser = userService.findByLoginName(loginName);
        if (exUser == null) {
            return CommonJson.dataResponse(CommonJson.ERROR, "用户不存在");
        }
        if (!StringUtils.equals(exUser.getPwd(), PwdUtils.encryptPwd(pwd))) {
            return CommonJson.dataResponse(CommonJson.ERROR, "登录密码不正确");
        }
        request.getSession().setAttribute(Const.ADMIN_SESSION, exUser);
        return CommonJson.dataResponse(CommonJson.SUCC, null);
    }

    @ResponseBody
    @RequestMapping("/loginOut")
    public String loginOut(HttpServletRequest request) {
        request.getSession().invalidate();
        return CommonJson.dataResponse(CommonJson.SUCC, null);
    }

    @ResponseBody
    @RequestMapping("/admin")
    public String doAdmin(HttpServletRequest request) {
        User exUser = userService.findByLoginName("admin");
        if (exUser != null) {
            return CommonJson.dataResponse(CommonJson.SUCC, "admin already exist.");
        }
        User admin = new User();
        admin.setUserId(IdWorkerUtils.usrIdWorker());
        admin.setLoginName("admin");
        admin.setPwd(PwdUtils.encryptPwd("admin"));
        admin.setUserRole(UserRoleEnum.SUPER);
        admin.setRealName("admin");
        admin.setMobile("13800000000");
        admin.setCreateTime(new Date());
        admin.setUpdTime(new Date());
        try {
            userService.save(admin);
            return CommonJson.dataResponse(CommonJson.SUCC, "admin user added");
        } catch (Exception e) {
            return CommonJson.dataResponse(CommonJson.ERROR, JSON.toJSONString(e.fillInStackTrace()));
        }
    }

    @RequestMapping("/welcome")
    public ModelAndView toWelcome(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("article/index");
        return mav;
    }
}
