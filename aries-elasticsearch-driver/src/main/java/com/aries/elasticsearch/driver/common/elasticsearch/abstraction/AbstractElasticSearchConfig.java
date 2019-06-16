package com.aries.elasticsearch.driver.common.elasticsearch.abstraction;

import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

/**
 * @author hanpeng7
 * Date Created in 2019-06-15 23:09
 */
public abstract class AbstractElasticSearchConfig {

    /**
     * 集群IP，支持传入多个IP，以逗号分隔
     */
    private String ips;
    /**
     * ES对应java客户端连接的端口
     */
    private Integer port;
    /**
     * 集群名称
     */
    private String clusterName;

    public String getIps() {
        return ips;
    }

    public void setIps(String ips) {
        this.ips = ips;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public TransportClient getTransportClient() throws UnknownHostException {
        // 启用嗅探，设置client.transport.sniff为true:
        Settings settings = Settings.builder()
                .put("client.transport.sniff", true)
                .put("cluster.name", clusterName)
                .build();
        TransportClient transportClient = new PreBuiltTransportClient(settings);
        // 将传入的多个IP拆分为数组(如果字符串中存在分号;,先将分号替换为为逗号以后再根据逗号进行分割成字符串数组)
        String[] esIps = ips.split(",");
        for (String esIp : esIps) {
            //添加集群IP列表
            transportClient.addTransportAddress(new TransportAddress(InetAddress.getByName(esIp), port));
        }
        return transportClient;
    }

    /**
     *  method to get TransportClient
     * @return TransportClient
     */
    abstract public TransportClient getClient() throws Exception;

    /**
     * 获取index admin管理者  通过下面这个API 就能获取index的管理者
     */
    abstract public IndicesAdminClient getIndicesAdminClient() throws Exception;

}
