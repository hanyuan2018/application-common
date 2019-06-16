package com.aries.common.threadpool.ariesthreadpool.web.service.impl;

import com.aries.common.threadpool.ariesthreadpool.web.service.AsyncServiceI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author hanp
 * @Description :
 * @Date Created in 2019/6/11 14:41
 */
@Service
public class AsyncServiceImpl implements AsyncServiceI {

    /**
     * 本地异常输出对象
     */
    private static final Logger logger = LoggerFactory.getLogger(AsyncServiceImpl.class);

    @Override
    @Async("asyncServiceExecutor")
    public void executeAsync() {
        logger.info("start executeAsync !");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            logger.error(" error" +
                    " information : " + e );
        }
        logger.info("end executeAsync !");
    }
}
