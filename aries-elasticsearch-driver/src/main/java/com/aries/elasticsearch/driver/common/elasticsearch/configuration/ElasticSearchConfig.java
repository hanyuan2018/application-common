package com.aries.elasticsearch.driver.common.elasticsearch.configuration;

import com.aries.elasticsearch.driver.common.elasticsearch.abstraction.AbstractElasticSearchConfig;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.client.transport.TransportClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * 通过 Ip、Port与集群名称获取ElasticSearch的连接
 *
 * @author hanpeng7
 * Date Created in 2019/06/15 23:42
 */
@Configuration
@ConfigurationProperties(prefix = "spring.data.elasticsearch.cluster")
public class ElasticSearchConfig extends AbstractElasticSearchConfig {

    /**
     * Title: getClient
     * Description: 项目启动后自动注入配置中
     *
     * @return TransportClient
     */
    @Primary
    @Bean(name = "transportClient")
    @Override
    public TransportClient getClient() throws Exception {
        return getTransportClient();
    }

    /**
     * 获取index admin管理者  通过下面这个API 就能获取index的管理者
     *
     * @return
     * @throws Exception
     */
    @Primary
    @Bean(name = "indicesAdminClient")
    @Override
    public IndicesAdminClient getIndicesAdminClient() throws Exception {
        return getTransportClient().admin().indices();
    }
}
