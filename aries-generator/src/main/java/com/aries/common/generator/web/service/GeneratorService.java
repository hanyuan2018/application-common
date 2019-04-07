package com.aries.common.generator.web.service;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aries.common.generator.common.utils.GeneratorUtils;
import com.aries.common.generator.common.utils.PageUtils;
import com.aries.common.generator.common.utils.Query;
import com.aries.common.generator.web.mapper.GeneratorMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@Service
public class GeneratorService {
	
	@Autowired
	private GeneratorMapper generatorMapper;
	
	public PageUtils queryList(Query query) {
		Page<?> page = PageHelper.startPage(query.getPageNow(), query.getPageSize());
		List<Map<String, Object>> list = generatorMapper.queryList(query);

		return new PageUtils((int)page.getTotal(), query.getPageSize(), query.getPageNow(), list);
	}

	public Map<String, Object> queryTable(String tableName) {
		return generatorMapper.queryTable(tableName);
	}

	public List<Map<String, Object>> queryColumns(String tableName) {
		return generatorMapper.queryColumns(tableName);
	}

	@SuppressWarnings("deprecation")
	public byte[] generatorCode(String[] tableNames) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ZipOutputStream zip = new ZipOutputStream(outputStream);

		for(String tableName : tableNames){
			//查询表信息
			Map<String, Object> table = queryTable(tableName);
			//查询列信息
			List<Map<String, Object>> columns = queryColumns(tableName);
			//生成代码
			GeneratorUtils.generatorCode(table, columns, zip);
		}
		IOUtils.closeQuietly(zip);
		return outputStream.toByteArray();
	}
}
