package com.lhl.onlinelearn.onlinelearn.service.impl;

import com.lhl.onlinelearn.onlinelearn.repository.UserRepository;
import com.lhl.onlinelearn.onlinelearn.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService {
    protected Logger logger =  LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserRepository userRepository;

    public Long countUser() {
        return userRepository.count();
    }
}
