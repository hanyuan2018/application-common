package com.aries.common.base.web.configuration;

import com.github.pagehelper.PageHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * 	mybatis的分页插件pageHelper的配置
 *
 * @author
 *
 */
@Configuration
public class PageHelperConfig {

    /**
     * 配置mybatis的分页插件pageHelper
     */
    @Bean
    public PageHelper pageHelper() {
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.setProperty("offsetAsPageNum", "true");
        properties.setProperty("rowBoundsWithCount", "true");
        properties.setProperty("reasonable", "true");
        properties.setProperty("dialect", "mysql"); // 配置mysql数据库的方言

        properties.setProperty("supportMethodsArguments", "true");
        properties.setProperty("returnPageInfo", "check");
        properties.setProperty("params", "count=countSql");
        System.out.println("配置mybatis的分页插件pageHelper ----> " + properties);
        pageHelper.setProperties(properties);
        return pageHelper;
    }

}
