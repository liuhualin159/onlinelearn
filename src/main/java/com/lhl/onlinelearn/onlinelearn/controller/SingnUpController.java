package com.lhl.onlinelearn.onlinelearn.controller;

import com.lhl.onlinelearn.onlinelearn.common.enums.IsDelete;
import com.lhl.onlinelearn.onlinelearn.entity.Collect;
import com.lhl.onlinelearn.onlinelearn.entity.User;
import com.lhl.onlinelearn.onlinelearn.repository.CollectRepository;
import com.lhl.onlinelearn.onlinelearn.repository.UserRepository;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/singnup")
public class SingnUpController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CollectRepository collectRepository;

    @GetMapping("")
    public ModelAndView toSingninPage() {
        return new ModelAndView("signup");
    }

    @PostMapping("/post")
    public ModelAndView doSignUp(@Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            Map data = new HashMap<String,Object>();
            data.put("errorMessage",bindingResult.getFieldError().getDefaultMessage());
            return new ModelAndView("signup",data);
        }
        user.setCreateTime(new Date().getTime());
        user.setLastModifyTime(new Date().getTime());
        userRepository.saveAndFlush(user);
        UsernamePasswordToken token = new UsernamePasswordToken();
        token.setUsername(user.getUserName());
        token.setPassword(user.getPassWord().toCharArray());
        SecurityUtils.getSubject().login(token);
        List<Collect> collectList = collectRepository.findByUserIdAndIsDelete(new Long(1), IsDelete.NO);
        Map data = new HashMap<String,Object>();
        data.put("collectList",collectList);
        return new ModelAndView("index",data);
    }
}
