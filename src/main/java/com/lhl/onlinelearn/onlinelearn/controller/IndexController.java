package com.lhl.onlinelearn.onlinelearn.controller;

import com.lhl.onlinelearn.onlinelearn.common.enums.IsDelete;
import com.lhl.onlinelearn.onlinelearn.entity.Collect;
import com.lhl.onlinelearn.onlinelearn.repository.CollectRepository;
import com.lhl.onlinelearn.onlinelearn.service.UrlLibraryService;
import com.lhl.onlinelearn.onlinelearn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

    @PostMapping("")
    public ModelAndView toIndexPage() {
        List<Collect> collectList = collectRepository.findByUserIdAndIsDelete(new Long(1), IsDelete.NO);
        Map data = new HashMap<String,Object>();
        data.put("collectList",collectList);
        return new ModelAndView("index",data);
    }

    @GetMapping("")
    public ModelAndView toIndexPageGet() {
        List<Collect> collectList = collectRepository.findByUserIdAndIsDelete(new Long(1), IsDelete.NO);
        Map data = new HashMap<String,Object>();
        data.put("collectList",collectList);
        return new ModelAndView("index",data);
    }

    @GetMapping("/getUserCount")
    public Long getUserCount() {
        return userService.countUser();
    }

    @GetMapping("/getCollectCount")
    public Long getCollectCount() {
        return collectRepository.count();
    }
}
