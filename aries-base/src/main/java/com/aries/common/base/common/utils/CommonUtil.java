package com.aries.common.base.common.utils;

import java.util.ArrayList;
import java.util.List;

public class CommonUtil {

    // 排除路径
    public static List<String> excludeUrlPath(){
        List<String> urlPathList = new ArrayList<>();
        urlPathList.add("/");
        urlPathList.add("/login");
        urlPathList.add("/logout");
        urlPathList.add("/api");
        return urlPathList;
    }

    // 排除资源请求
    public static List<String> excludeResourcePath(){
        List<String>  resourcePathList = new ArrayList<>();
        resourcePathList.add("/css/**");
        resourcePathList.add("/font-awesome/**");
        resourcePathList.add("/images/**");
        resourcePathList.add("/img/**");
        resourcePathList.add("/js/**");
        resourcePathList.add("/plugins/**");
        return resourcePathList;
    }

}
