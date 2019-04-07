package com.aries.common.generator.common.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.aries.common.generator.common.utils.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AriesExceptionHandler implements HandlerExceptionResolver {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object o, Exception ex) {
    	Result r = new Result();
		try {
			response.setContentType("application/json;charset=utf-8");
			response.setCharacterEncoding("utf-8");
			
			if (ex instanceof AriesException) {
				r.put("code", ((AriesException) ex).getCode());
				r.put("msg", ((AriesException) ex).getMessage());
			}else if(ex instanceof DuplicateKeyException){
				r = Result.error("数据库中已存在该记录");
			}else{
				r = Result.error();
			}
			
			//记录异常日志
			logger.error(ex.getMessage(), ex);
			
			String json = JSON.toJSONString(r);
			response.getWriter().print(json);
		} catch (Exception e) {
			logger.error("RRExceptionHandler 异常处理失败", e);
		}
		return new ModelAndView();
    }
}
