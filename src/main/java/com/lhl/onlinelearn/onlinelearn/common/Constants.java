package com.lhl.onlinelearn.onlinelearn.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Constants {

    /** 默认图标 */
    public static String default_logo="img/logo.jpg";

    public static String user_Agent="Mozilla";

    public static String BASE_PATH;

    @Autowired(required = true)
    public void setBasePath(@Value("${ollearn.base.path}")String basePath) {
        Constants.BASE_PATH = basePath;
    }
}
