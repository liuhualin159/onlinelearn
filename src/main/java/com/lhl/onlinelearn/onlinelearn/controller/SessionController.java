package com.lhl.onlinelearn.onlinelearn.controller;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/sessions")
public class SessionController {
    @Autowired
    private SessionDAO sessionDAO;

    @RequestMapping("/count")
    public String list() {
        Collection<Session> sessions =  sessionDAO.getActiveSessions();
        return String.valueOf(sessions.size());
    }
}
