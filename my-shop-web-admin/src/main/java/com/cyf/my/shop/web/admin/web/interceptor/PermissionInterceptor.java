package com.cyf.my.shop.web.admin.web.interceptor;

import com.cyf.my.shop.commons.constant.ConstantUtils;
import com.cyf.my.shop.domain.TbUser;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PermissionInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if(modelAndView!=null&&modelAndView.getViewName()!=null&&modelAndView.getViewName().endsWith("login")){
            TbUser tbUser=(TbUser)request.getSession().getAttribute(ConstantUtils.SESSION_USER);
            if(tbUser!=null){
                response.sendRedirect("/main");
            }
        }

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
