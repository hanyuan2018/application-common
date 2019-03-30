package com.aries.common.generator.web.mapper;

import java.util.List;
import java.util.Map;


/**
 * 查询数据表信息接口
 * 
 * @author hanp
 *
 */
public interface GeneratorMapper {
	
	List<Map<String, Object>> queryList(Map<String, Object> map);
	
    Map<String, Object> queryTable(String tableName);

    List<Map<String, Object>> queryColumns(String tableName);
}
