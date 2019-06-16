package com.aries.common.threadpool.ariesthreadpool.web.controller;

import com.aries.common.threadpool.ariesthreadpool.web.service.AsyncServiceI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hanp
 * @Description :
 * @Date Created in 2019/6/11 14:50
 */
@RestController
public class AsyncController {

    /**
     * 本地异常输出对象
     */
    private static final Logger logger = LoggerFactory.getLogger(AsyncController.class);

    @Autowired
    private AsyncServiceI asyncServiceI;

    @RequestMapping("/submit")
    public String submit() {
        logger.info(" start submit !");
        // 调用service层的业务方法
        asyncServiceI.executeAsync();
        logger.info(" end submit !");
        return "success";
    }


}
