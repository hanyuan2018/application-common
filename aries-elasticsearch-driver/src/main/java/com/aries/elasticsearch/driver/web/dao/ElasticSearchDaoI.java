package com.aries.elasticsearch.driver.web.dao;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.query.QueryBuilder;

import java.util.Map;

/**
 * ES通用查询方法接口类
 *
 * @author hanpeng7
 * Date Created in 2019-06-16 0:02
 */
public interface ElasticSearchDaoI {

    /**
     * 判断对应的index是否存在
     *
     * @param indexName -- es对应的index
     * @return Boolean
     */
    Boolean isExist(String indexName) throws Exception;

    /**
     * 创建index
     *
     * @param indexName
     * @return String
     */
    Boolean createIndex(String indexName) throws Exception;

    /**
     * 创建索引index,并设置主分片数与副分片数
     *
     * @param indexName      索引index
     * @param numberShards   主分片数
     * @param numberReplicas 副本个数
     * @return
     */
    Boolean createIndex(String indexName, int numberShards, int numberReplicas) throws Exception;

    /**
     * 删除索引index
     *
     * @param indexName
     * @return Boolean
     */
    Boolean deleteIndex(String indexName) throws Exception;

    /**
     * 设置索引index的type与mapping信息
     *
     * @param indexName
     * @param typeName
     * @param mapping
     * @return
     */
    Boolean setIndexMapping(String indexName, String typeName, XContentBuilder mapping) throws Exception;

    /**
     * 通过index查询对应数据信息
     *
     * @param queryBuilder 拼接的查询条件
     * @param indexName    index名称
     * @return SearchResponse
     */
    SearchResponse searchQuery(QueryBuilder queryBuilder, String indexName) throws Exception;

    /**
     * 通过index与type查询对应数据信息
     *
     * @param queryBuilder 拼接的查询条件
     * @param indexName    index名称
     * @param typeName     type名称
     * @return SearchResponse
     */
    SearchResponse searchQuery(QueryBuilder queryBuilder, String indexName, String typeName) throws Exception;

    /**
     * 通过index，使用游标查询
     *
     * @param queryBuilder 查询条件
     * @param indexName    index名称
     * @param Size         每批读取的数据量
     * @param timeValue    每次查询数据的有效期时间
     * @return
     */
    Map<String, Object> searchScrollQuery(QueryBuilder queryBuilder, String indexName, int Size, int timeValue) throws Exception;

    /**
     * 通过index与type，使用游标查询
     *
     * @param queryBuilder 查询条件
     * @param indexName    index名称
     * @param typeName     type名称
     * @param Size         每批读取的数据量
     * @param timeValue    每次查询数据的有效期时间
     * @return
     */
    Map<String, Object> searchScrollQuery(QueryBuilder queryBuilder, String indexName, String typeName, int Size, int timeValue) throws Exception;

}
