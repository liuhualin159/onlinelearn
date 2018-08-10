package com.lhl.onlinelearn.onlinelearn.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/singn")
public class LoginController {
    @GetMapping("")
    public ModelAndView toSingninPage() {
        return new ModelAndView("signin");
    }

    @PostMapping("/login")
    ModelAndView doLogin() {
    //添加用户认证信息
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("1","1");
        try{
            //进行验证，这里可以捕获异常，然后返回对应信息
            subject.login(token);
        }
        catch(Exception e){
            return new ModelAndView("signin");
        }

        return new ModelAndView("/index");
    }
}
