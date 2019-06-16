package com.aries.elasticsearch.driver.web.dao.impl;

import com.aries.elasticsearch.driver.web.dao.ElasticSearchDaoI;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.action.search.ClearScrollRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hanpeng7
 * Date Created in 2019-06-16 0:02
 */
@Service
public class ElasticSearchDaoImpl implements ElasticSearchDaoI {

    private final static Logger logger = LoggerFactory.getLogger(ElasticSearchDaoImpl.class);

    /**
     * 注入连接Client
     */
    @Autowired
    @Qualifier(value = "transportClient")
    private TransportClient transportClient;

    /**
     * 注入连接 IndicesAdminClient, 获取index admin管理者
     */
    @Autowired
    @Qualifier(value = "indicesAdminClient")
    private IndicesAdminClient indicesAdminClient;

    @Override
    public Boolean isExist(String indexName) throws Exception {
        // 执行判断index是否存在
        IndicesExistsResponse response = indicesAdminClient.prepareExists(indexName.toLowerCase())
                .execute()
                .actionGet();
        return response.isExists();
    }

    @Override
    public Boolean createIndex(String indexName) throws Exception {
        /**
         * 用获取到的index管理对象去创建index
         * 默认该索引的主分片个数为 3
         * 默认该索引的副本个数为 2
         */
        Settings.Builder settings = Settings.builder()
                .put("index.number_of_shards", 3)
                .put("index.number_of_replicas", 2);
        // 执行创建index
        CreateIndexResponse response = indicesAdminClient.prepareCreate(indexName.toLowerCase())
                .setSettings(settings)
                .execute()
                .actionGet();
        //  如果操作成功则返回true  失败则返回false
        return response.isShardsAcknowledged();
    }

    @Override
    public Boolean createIndex(String indexName, int numberShards, int numberReplicas) throws Exception {
        /**
         * 用获取到的index管理对象去创建index
         * 默认该索引的主分片个数为 numberShards
         * 默认该索引的副本个数为 numberReplicas
         */
        Settings.Builder settings = Settings.builder()
                .put("index.number_of_shards", numberShards)
                .put("index.number_of_replicas", numberReplicas);
        // 执行创建index操作
        CreateIndexResponse response = indicesAdminClient.prepareCreate(indexName.toLowerCase())
                .setSettings(settings)
                .execute()
                .actionGet();
        //  如果操作成功则返回true  失败则返回false
        return response.isShardsAcknowledged();
    }

    @Override
    public Boolean deleteIndex(String indexName) throws Exception {
        // 执行删除index的操作
        DeleteIndexResponse response = indicesAdminClient.prepareDelete(indexName.toLowerCase())
                .execute()
                .actionGet();
        //  如果操作成功则返回true  失败则返回false
        return response.isAcknowledged();
    }

    @Override
    public Boolean setIndexMapping(String indexName, String typeName, XContentBuilder mapping) throws Exception {
        // 执行创建对应index 的 mapping
        PutMappingResponse response = indicesAdminClient.preparePutMapping(indexName.toLowerCase())
                .setType(typeName.toUpperCase())
                .setSource(mapping)
                .execute()
                .actionGet();
        //  如果操作成功则返回true  失败则返回false
        return response.isAcknowledged();
    }

    @Override
    public SearchResponse searchQuery(QueryBuilder queryBuilder, String indexName) throws Exception {
        SearchResponse response = transportClient.prepareSearch(indexName.toLowerCase())
                .setQuery(queryBuilder)
                .execute()
                .actionGet();
        return response;
    }

    @Override
    public SearchResponse searchQuery(QueryBuilder queryBuilder, String indexName, String typeName) throws Exception {
        SearchResponse response = transportClient.prepareSearch(indexName.toLowerCase())
                .setTypes(typeName.toUpperCase())
                .setQuery(queryBuilder)
                .execute()
                .actionGet();
        return response;
    }

    @Override
    public Map<String, Object> searchScrollQuery(QueryBuilder queryBuilder, String indexName, int size, int timeValue) throws Exception {
        Map<String, Object> resultMap = scrollQuery(queryBuilder, indexName, null, size, timeValue);
        return resultMap;
    }

    @Override
    public Map<String, Object> searchScrollQuery(QueryBuilder queryBuilder, String indexName, String typeName, int size, int timeValue) throws Exception {
        Map<String, Object> resultMap = scrollQuery(queryBuilder, indexName, typeName, size, timeValue);
        return resultMap;
    }

    /**
     * @param queryBuilder
     * @param indexName
     * @param typeName
     * @param size
     * @param timeValue
     * @return
     */
    private Map<String, Object> scrollQuery(QueryBuilder queryBuilder, String indexName, String typeName, int size, int timeValue) {
        Map<String, Object> resultMap = new HashMap<>();
        // 设置索引名称,搜索数量,发送请求
        SearchRequestBuilder responseBuilder = null;
        if (StringUtils.isEmpty(typeName)) {
            responseBuilder = transportClient.prepareSearch(indexName.toLowerCase()).setQuery(queryBuilder);
        } else {
            responseBuilder = transportClient.prepareSearch(indexName.toLowerCase()).setTypes(typeName.toUpperCase()).setQuery(queryBuilder);
        }
        SearchResponse searchResponse = responseBuilder
                //.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setSize(size)
                .setScroll(TimeValue.timeValueMillis(timeValue))
                .execute()
                .actionGet();
        // 将查询结果放于List集合对象中
        List<Map<String, Object>> resultList = new ArrayList<>();
        //获得首次的查询结果
        long totalHits = searchResponse.getHits().getTotalHits();
        // 获取首次查询出的结果
        for (SearchHit searchHit : searchResponse.getHits().getHits()) {
            Map<String, Object> sourceAsMap = searchHit.getSourceAsMap();
            resultList.add(sourceAsMap);
        }
        // 将scorllId循环传递
        do {
            //将scorllId循环传递
            searchResponse = transportClient.prepareSearchScroll(searchResponse.getScrollId())
                    .setScroll(TimeValue.timeValueMillis(timeValue))
                    .execute()
                    .actionGet();
            for (SearchHit searchHit : searchResponse.getHits().getHits()) {
                Map<String, Object> sourceAsMap = searchHit.getSourceAsMap();
                resultList.add(sourceAsMap);
            }
            //当searchHits的数组为空的时候结束循环，至此数据全部读取完毕
        } while (searchResponse.getHits().getHits().length != 0);
        //删除scroll
        ClearScrollRequest clearScrollRequest = new ClearScrollRequest();
        clearScrollRequest.addScrollId(searchResponse.getScrollId());
        transportClient.clearScroll(clearScrollRequest).actionGet();
        // 将要返回的数据存放于返回对象中
        resultMap.put("totalCount", totalHits);
        resultMap.put("result", resultList);
        return resultMap;
    }
}
