package com.aries.common.generator.common.utils;

import java.util.HashMap;
import java.util.Map;

public class Result extends HashMap<String, Object> {

	private static final long serialVersionUID = 1L;
	
	public static Result success() {
		return new Result();
	}
	
	public static Result success(String msg) {
		Result result = new Result();
		result.put("msg", msg);
		return result;
	}
	
	public static Result success(Map<String, Object> map) {
		Result result = new Result();
		result.putAll(map);
		return result;
	}
	
	public static Result error() {
		return error(1000, "未知异常，请联系管理员！！！");
	}
	
	public static Result error(String msg) {
		return error(500, msg);
	}
	
	public static Result error(int code, String msg) {
		Result result = new Result();
		result.put("code", code);
		result.put("msg", msg);
		return result;
	}
	
	public Result put(String key, String value) {
		super.put(key, value);
		return this;
	}
}
