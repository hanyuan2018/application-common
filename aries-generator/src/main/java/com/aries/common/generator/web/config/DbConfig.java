package com.aries.common.generator.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import com.aries.common.generator.web.mapper.GeneratorMapper;
import com.aries.common.generator.web.mapper.MySqlGeneratorMapper;
import com.aries.common.generator.web.mapper.OracleGeneratorMapper;
import com.aries.common.generator.web.mapper.PostgreSqlGeneratorMapper;
import com.aries.common.generator.web.mapper.SqlServerGeneratorMapper;

/**
 * 数据库配置
 * 
 * @author hanp
 *
 */
public class DbConfig {

	@Value("${aries.database}")
	private String database;
	@Autowired
	private MySqlGeneratorMapper mysqlGeneratorMapper;
	@Autowired
	private OracleGeneratorMapper oracleGeneratorMapper;
	@Autowired
	private SqlServerGeneratorMapper sqlServerGeneratorMapper;
	@Autowired
	private PostgreSqlGeneratorMapper postgreSqlGeneratorMapper;

	@Bean
	@Primary
	public GeneratorMapper getGeneratorMapper() throws Exception {
		if ("mysql".equals(database.toLowerCase())) {
			return mysqlGeneratorMapper;
		} else if ("oracle".equals(database.toLowerCase())) {
			return oracleGeneratorMapper;
		} else if ("sqlserver".equals(database.toLowerCase())) {
			return sqlServerGeneratorMapper;
		} else if ("postgresql".equals(database.toLowerCase())) {
			return postgreSqlGeneratorMapper;
		} else {
			throw new Exception("不支持当前数据库：" + database);
		}
	}
}
