package com.aries.common.base.web.controller.index;

import com.aries.common.base.common.constants.ForwardConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

    @RequestMapping(value = "index", method = RequestMethod.GET, produces = "text/html; charset=utf-8")
    public String toIndex(HttpServletRequest request) {
        return ForwardConstants.INDEX;
    }

    @RequestMapping("main")
    public String main() {
        return ForwardConstants.MAIN;
    }
}
