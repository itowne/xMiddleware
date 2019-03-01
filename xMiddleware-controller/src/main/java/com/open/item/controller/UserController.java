package com.open.item.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.open.item.beans.DataTableBean;
import com.open.item.entity.Page;
import com.open.item.entity.User;
import com.open.item.entity.enumObject.StatEnum;
import com.open.item.entity.enumObject.UserRoleEnum;
import com.open.item.service.UserService;
import com.open.item.utils.CommonJson;
import com.open.item.utils.IdWorkerUtils;
import com.open.item.utils.PwdUtils;

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
    @RequestMapping(value = "/userList")
    public DataTableBean<User> userList(HttpServletRequest request, int start, int length) {
        User u = sessionUser();
        String loginName = null;
        if (!isAdmin()) {
            loginName = u.getLoginName();
        }
        Page<User> page = userService.findUserPage(start, length, loginName);
        DataTableBean<User> dtb = new DataTableBean<User>(page);
        return dtb;
    }

    @RequestMapping(value = "/toAddUser")
    public ModelAndView toAdd(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        if (isAdmin()) {
            mav.addObject("isAdmin", "1");
            mav.addObject("roleAdmin", roleSelect());
        }
        mav.setViewName("user/add");
        return mav;
    }

    @ResponseBody
    @RequestMapping(value = "/doAddUser")
    public String doAdd(HttpServletRequest request) {
        String loginName = request.getParameter("loginName");
        String realName = request.getParameter("realName");
        String pwd = request.getParameter("pwd");
        String mobile = request.getParameter("mobile");
        String userRole = request.getParameter("userRole");
        User exUser = userService.findByLoginName(loginName);
        if (exUser != null) {
            return CommonJson.dataResponse(CommonJson.ERROR, "用户名已存在!");
        }
        if (StringUtils.isBlank(userRole) && isAdmin()) {
            return CommonJson.dataResponse(CommonJson.ERROR, "请选择一个角色!");
        }
        User u = new User();
        u.setLoginName(loginName);
        u.setRealName(realName);
        u.setPwd(PwdUtils.encryptPwd(pwd));
        u.setMobile(mobile);
        u.setCreateTime(new Date());
        u.setUpdTime(new Date());
        u.setUserId(IdWorkerUtils.usrIdWorker());
        u.setUserStat(StatEnum.VALID);
        u.setUserRole(UserRoleEnum.valueOf(userRole));
        try {
            userService.save(u);
            return CommonJson.dataResponse(CommonJson.SUCC, null);
        } catch (Exception e) {
            logger.info("新增用户失败！原因:{}", e.getMessage());
            return CommonJson.dataResponse(CommonJson.ERROR, e.getMessage());
        }
    }

    @RequestMapping(value = "/index")
    public ModelAndView index(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        if (isAdmin()) {
            mav.addObject("isAdmin", "1");
        }
        User u = sessionUser();
        mav.addObject("loginName", u.getLoginName());
        mav.setViewName("user/index");
        return mav;
    }

    @RequestMapping(value = "/toEditUser/{userId}")
    public ModelAndView toEdit(HttpServletRequest request, @PathVariable String userId) {
        ModelAndView mav = new ModelAndView();
        User u = userService.findById(userId);
        if (u == null) {
            mav.setViewName("user/index");
            return mav;
        }
        Map<String, String> roleMap = roleSelect();
        if (isAdmin()) {
            mav.addObject("isAdmin", "1");
            roleMap.put(UserRoleEnum.SUPER.name(), UserRoleEnum.SUPER.getLabel());
        }
        mav.addObject("roleAdmin", roleMap);
        mav.setViewName("user/edit");
        mav.addObject("user", u);
        return mav;
    }

    @ResponseBody
    @RequestMapping(value = "/doEditUser")
    public String doEdit(HttpServletRequest request) {
        String realName = request.getParameter("realName");
        String pwd = request.getParameter("pwd");
        String mobile = request.getParameter("mobile");
        String userId = request.getParameter("userId");
        String userRole = request.getParameter("userRole");
        User u = userService.findById(userId);
        if (u == null) {
            return CommonJson.dataResponse(CommonJson.ERROR, "未找到该记录");
        }
        if (StringUtils.isBlank(userRole) && isAdmin()) {
            return CommonJson.dataResponse(CommonJson.ERROR, "请选择一个角色!");
        }
        if (!pwd.equals(u.getPwd())) {
            u.setPwd(PwdUtils.encryptPwd(pwd));
        }
        u.setRealName(realName);
        u.setMobile(mobile);
        if (isAdmin()) {
            u.setUserRole(UserRoleEnum.valueOf(userRole));
        }
        try {
            userService.update(u);
            return CommonJson.dataResponse(CommonJson.SUCC, null);
        } catch (Exception e) {
            logger.info("修改用户失败!错误原因:{}", e.getMessage());
            return CommonJson.dataResponse(CommonJson.ERROR, e.getMessage());
        }
    }

    @ResponseBody
    @RequestMapping(value = "/delUser/{userId}")
    public String delUser(HttpServletRequest request, @PathVariable String userId) {
        User u = userService.findById(userId);
        if (u == null) {
            CommonJson.dataResponse(CommonJson.ERROR, "查询不到该数据");
        }
        try {
            userService.deleteUser(u);
            return CommonJson.dataResponse(CommonJson.SUCC, null);
        } catch (Exception e) {
            logger.info("删除用户失败!错误原因:{}", e.getMessage());
            return CommonJson.dataResponse(CommonJson.ERROR, e.getMessage());
        }
    }

    private Map<String, String> roleSelect() {
        Map<String, String> targetMap = new HashMap<>();
        for (UserRoleEnum role : UserRoleEnum.values()) {
            if (role == UserRoleEnum.SUPER) {
                continue;
            }
            targetMap.put(role.name(), role.getLabel());
        }
        return targetMap;
    }

}
