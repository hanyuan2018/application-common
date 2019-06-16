package com.aries.common.threadpool.ariesthreadpool.configuration;

import com.aries.common.threadpool.ariesthreadpool.web.executor.VisiableThreadPoolTaskExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * springboot的线程池配置
 *
 * @author hanp
 * Created in 2019/6/11 14:57
 */
@EnableAsync
@Configuration
public class ExecutorConfig {

    /**
     * 本地异常输出对象
     */
    private static final Logger logger = LoggerFactory.getLogger(ExecutorConfig.class);

    /**
     * 修改ExecutorConfig.java的asyncServiceExecutor方法，
     * 将ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor()
     * 改为ThreadPoolTaskExecutor executor = new VisiableThreadPoolTaskExecutor()
     *
     * @return
     */

    @Bean
    public Executor asyncServiceExecutor() {
        logger.info(" start asyncServiceExecutor !");
        //ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        ThreadPoolTaskExecutor executor = new VisiableThreadPoolTaskExecutor();
        // 配置核心线程数
        executor.setCorePoolSize(5);
        // 配置最大线程数
        executor.setMaxPoolSize(5);
        // 配置队列大小
        executor.setQueueCapacity(99999);
        // 配置线程池中的线程的名称前缀
        executor.setThreadNamePrefix("async-service-");
        // * rejection-policy : 当pool已经达到max size的时候，如何处理新线程
        // * CALLER_RUNS : 不在新的线程中执行任务，而是有调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 初始化
        executor.initialize();
        return executor;
    }

}
