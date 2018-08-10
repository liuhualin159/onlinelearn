package com.lhl.onlinelearn.onlinelearn.controller;

import com.lhl.onlinelearn.onlinelearn.controller.result.ExceptionMsg;
import com.lhl.onlinelearn.onlinelearn.controller.result.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

@Controller
public class BaseController {
    protected Logger logger =  LoggerFactory.getLogger(this.getClass());

    protected Response result(ExceptionMsg msg){
        return new Response(msg);
    }
    protected Response result(){
        return new Response();
    }
}
