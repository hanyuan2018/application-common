package com.aries.common.base.web.interceptor;

import com.aries.common.base.common.utils.SessionUtil;
import com.aries.common.base.web.entity.SysUserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Component
public class LoginInterceptor implements HandlerInterceptor {

    // 本地异常日志记录对象
    private static Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // (对用户登陆做session check)
        Object userSession = SessionUtil.getUserSession();
        logger.info("项目访问路径：" +  request.getContextPath());
        if( null == userSession){
            // 未登录，重定向到登录页
            response.sendRedirect(request.getContextPath() + "");
            return false;
        }
        SysUserEntity sysUserEntity = (SysUserEntity) userSession;
        logger.info("当前用户已登录，登录的用户名为： " +  sysUserEntity.getUserName());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
