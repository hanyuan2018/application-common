package com.aries.common.generator.common.utils;

import java.util.LinkedHashMap;
import java.util.Map;

public class Query extends LinkedHashMap<String, Object>{

	private static final long serialVersionUID = 1L;
	
	// 当前页
	private Integer pageNow = 1;
	// 每页条数
	private Integer pageSize = 10;
	
	public Integer getPageNow() {
		return pageNow;
	}
	public void setPageNow(Integer pageNow) {
		this.pageNow = pageNow;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
	public Query(Map<String, Object> params) {
		
		this.putAll(params);
		// 获取分页参数
		String pageNowi = params.get("pageNow") != null ? params.get("pageNow").toString() : "1";
		String pageSizei = params.get("pageSize") != null ? params.get("pageSize").toString() : "10";
		this.pageNow = Integer.parseInt(pageNowi);
		this.pageSize = Integer.parseInt(pageSizei);
		this.put("start", (pageNow - 1) * pageSize);
		this.put("pageNow", pageNow);
		this.put("pageSize", pageSize);
		
	}
	
}
