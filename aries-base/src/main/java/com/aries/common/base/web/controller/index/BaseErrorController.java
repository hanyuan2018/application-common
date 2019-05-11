package com.aries.common.base.web.controller.index;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 修改访问资源路径报错，跳转对应error的页面方法
 * @author hanp
 *
 */
@Controller
public class BaseErrorController implements ErrorController {

    private static final String ERROR_PATH = "/error";

    @RequestMapping(value=ERROR_PATH)
    public String handleError() {
        return "404";
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }
}
