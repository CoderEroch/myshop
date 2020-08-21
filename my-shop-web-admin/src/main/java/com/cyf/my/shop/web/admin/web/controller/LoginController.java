package com.cyf.my.shop.web.admin.web.controller;


import com.cyf.my.shop.commons.constant.ConstantUtils;
import com.cyf.my.shop.domain.TbUser;
import com.cyf.my.shop.web.admin.service.TbUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {
    @Autowired
    private TbUserService tbUserService;
    @RequestMapping(value = {"","login"},method = RequestMethod.GET)
    public String login(){
        return "login";
    }
    @RequestMapping(value="login",method = RequestMethod.POST)
    public String login(String email, String password,
                        HttpServletRequest httpServletRequest, Model model){
        TbUser tbUser=tbUserService.login(email,password);
        //登陆失败
        if(tbUser==null){
            model.addAttribute("message","用户名或者密码错误请重新输入");
            return login();
        }
        //登录成功
        else{
            httpServletRequest.getSession().setAttribute(ConstantUtils.SESSION_USER,tbUser);
            return "redirect:/main";
        }
    }
    @RequestMapping(value = "logout",method = RequestMethod.GET)
    public String logout(HttpServletRequest request){
        request.getSession().invalidate();
        return login();
    }

}
