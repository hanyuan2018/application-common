package com.aries.common.base.web.configuration;

import com.aries.common.base.common.utils.CommonUtil;
import com.aries.common.base.web.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 登录拦截配置
 */
public class LoginConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册拦截器
        InterceptorRegistration loginRegistration = registry.addInterceptor(loginInterceptor);
        // 拦截路径
        loginRegistration.addPathPatterns("/**");
        // 排除路径
        loginRegistration.excludePathPatterns(CommonUtil.excludeUrlPath());
        // 排除资源请求
        loginRegistration.excludePathPatterns(CommonUtil.excludeResourcePath());

        System.out.println("Springboot 登录拦截配置： ----> " + loginRegistration );
    }
}
