package com.lhl.onlinelearn.onlinelearn.controller;

import com.lhl.onlinelearn.onlinelearn.aop.LogManage;
import com.lhl.onlinelearn.onlinelearn.common.enums.IsDelete;
import com.lhl.onlinelearn.onlinelearn.entity.Collect;
import com.lhl.onlinelearn.onlinelearn.entity.User;
import com.lhl.onlinelearn.onlinelearn.repository.CollectRepository;
import com.lhl.onlinelearn.onlinelearn.service.UserService;
import com.lhl.onlinelearn.onlinelearn.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/")
public class IndexController  extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private CollectRepository collectRepository;

    @RequestMapping(value = "", method = {RequestMethod.POST,RequestMethod.GET})
    @LogManage(description = "打开首页")
    public ModelAndView toIndexPageGet() {
        User user = UserUtils.getCurrentUser();
        List<Collect> collectList = collectRepository.findByUserIdAndIsDelete(user.getId(), IsDelete.NO);
        Map data = new HashMap<String,Object>();
        data.put("collectList",collectList);
        return new ModelAndView("index",data);
    }

    @GetMapping("/getUserCount")
    @LogManage(description = "取得用户总数")
    public Long getUserCount() {
        return userService.countUser();
    }

    @GetMapping("/getCollectCount")
    @LogManage(description = "取得在线用户")
    public Long getCollectCount() {
        return collectRepository.count();
    }
}
