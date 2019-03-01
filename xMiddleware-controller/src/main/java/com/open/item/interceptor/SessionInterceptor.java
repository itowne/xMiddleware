package com.open.item.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.open.item.beans.Const;
import com.open.item.entity.User;

public class SessionInterceptor implements HandlerInterceptor {

    private String HOME_URL = "/login";

    /*
     * 该方法将在请求处理之前进行调用，只有该方法返回true，才会继续执行后续的Interceptor和Controller，当返回值为true
     * 时就会继续调用下一个Interceptor的preHandle
     * 方法，如果已经是最后一个Interceptor的时候就会是调用当前请求的Controller方法；
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj) throws Exception {
        if (null == request) {
            return false;
        }
        if (null == request.getSession().getAttribute(Const.ADMIN_SESSION)) {
            if (request.getHeader("accept").indexOf("application/json") != -1) {
                response.setHeader("sessionstatus", "timeout"); // 响应头设置session状态
                response.getWriter().write(JSON.toJSONString("timeout"));
                return false; // session超时，ajax访问返回false
            }
            response.sendRedirect(request.getContextPath() + HOME_URL);
            return false;
        }
        return true;
    }

    /*
     * 该方法也是需要当前对应的Interceptor的preHandle方法的返回值为true时才会执行，该方法将在整个请求结束之后，
     * 也就是在DispatcherServlet 渲染了对应的视图之后执行。用于进行资源清理。
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object obj, Exception e)
            throws Exception {

    }

    /*
     * 该方法将在请求处理之后，DispatcherServlet进行视图返回渲染之前进行调用，可以在这个方法中对Controller
     * 处理之后的ModelAndView 对象进行操作。
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object obj, ModelAndView mav)
            throws Exception {
        if (mav != null) {
            User user = (User) request.getSession().getAttribute(Const.ADMIN_SESSION);
            if (user != null) {
                mav.addObject("userRoleLabel", user.getUserRoleLabel());
                mav.addObject("userLoginName", user.getLoginName());
            }
        }
    }

}
