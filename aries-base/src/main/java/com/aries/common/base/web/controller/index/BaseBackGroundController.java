package com.aries.common.base.web.controller.index;

import com.aries.common.base.common.constants.AttrConstants;
import com.aries.common.base.common.constants.ForwardConstants;
import org.apache.catalina.manager.util.SessionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/")
public class BaseBackGroundController extends  BaseController{

    /**
     * 登陆画面
     *
     * @return
     */
    @RequestMapping(value = "")
    public String toIndex() {
        return ForwardConstants.REDIRECT + ForwardConstants.LOGIN;
    }

    /**
     * 进入登陆画面
     */
    @RequestMapping(value = "login", method = RequestMethod.GET, produces = "text/html; charset=utf-8")
    public String login(HttpServletRequest request) {
        return ForwardConstants.LOGIN;
    }

    /**
     * 用户登录方法
     *
     * @param request
     * @param userName
     * @param password
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "login", method = RequestMethod.POST, produces = "text/html; charset=utf-8")
    public String login(HttpServletRequest request, String userName, String password){
        // 判断用户名与参数是否为空
        if(StringUtils.isNotEmpty(userName) || StringUtils.isNotEmpty(password)){
            return AttrConstants.EMPTY;
        }



        return AttrConstants.SUCCESS;
    }

    @RequestMapping(value = "index", method = RequestMethod.GET, produces = "text/html; charset=utf-8")
    public String toIndex(HttpServletRequest request) {
        return ForwardConstants.INDEX;
    }

    @RequestMapping("main")
    public String main() {
        return ForwardConstants.MAIN;
    }
}
