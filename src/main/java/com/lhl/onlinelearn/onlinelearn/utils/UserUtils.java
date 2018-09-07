package com.lhl.onlinelearn.onlinelearn.utils;

import com.lhl.onlinelearn.onlinelearn.entity.User;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.shiro.SecurityUtils;

public class UserUtils {
    public  static User getCurrentUser() {
        User user = new User();
        try {
            BeanUtils.copyProperties(user,SecurityUtils.getSubject().getPrincipal());
        } catch (Exception ex) {

        }
        return  user;
    }
}
