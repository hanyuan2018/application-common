package com.aries.common.generator.common.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;

/**
 * 代码生成器工具类
 * 
 * @author hanp
 *
 */
public class GeneratorUtils {
	
	public static List<String> getTemplates(){
		List<String> templates = new ArrayList<String>();
		templates.add("template/Entity.java.vm");
		templates.add("template/Mapper.java.vm");
		templates.add("template/Mapper.xml.vm");
		templates.add("template/Service.java.vm");
		templates.add("template/ServiceImpl.java.vm");
		templates.add("template/Controller.java.vm");
		templates.add("template/menu.sql.vm");
		
		templates.add("template/index.vue.vm");
		templates.add("template/add-or-update.vue.vm");
		return templates;
	}
	
	/**
	 * 列名转换成Java属性名
	 */
	public static String columnToJava(String column) {
		return WordUtils.capitalize(column, new char[] {'_'}).replace("_", "");
	}
	
	/**
	 * 表名转换成Java类名
	 */
	public static String columnToJava(String tableName, String tablePrefix) {
		if(StringUtils.isNotBlank(tablePrefix)) {
			tableName = tableName.replaceFirst(tablePrefix, "");
		}
		return columnToJava(tableName);
	}
	
	
	/**
	 * 获取配置文件信息
	 * @throws Exception 
	 */
	public static Configuration getConfig() throws Exception {
		try {
			return new PropertiesConfiguration("generator.properties");
		} catch (ConfigurationException e) {
			e.printStackTrace();
			throw new Exception("获取配置文件失败！");
		}
	}
	
	/**
	 * 获取文件名
	 */
	public static String getFileName(String template, String className, String packageName, String moduleName) {
		String packagePath = "" + File.separator + "java" + File.separator;
		if(StringUtils.isNotBlank(packageName)) {
			packagePath += packageName.replace(".", File.separator) + File.separator + moduleName + File.separator;
		}
		//
		if(template.contains("Entity.java.vm")) {
			return packagePath + "entity" + File.separator + className + "Entity.java";
		}
		//
		if(template.contains("Mapper.java.vm")) {
			return packagePath + "mapper" + File.separator + className + "Mapper.java";
		}
		//
		if(template.contains("Mapper.xml.vm")) {
			return "main" + File.separator + "resources" + File.separator + "mappings" + File.separator + moduleName + File.separator + className + "Dao.xml";
		}
		//
		if(template.contains("Service.java.vm")) {
			return packagePath + "service" + File.separator + className + "Service.java";
		}
		//
		if(template.contains("ServiceImpl.java.vm")) {
			return packagePath + "service" + File.separator + "impl" + File.separator + className + "ServiceImpl.java";
		}
		//
		if(template.contains("Controller.java.vm")) {
			return packagePath + "controller" + File.separator + className + "Controller.java";
		}
		//
		if(template.contains("menu.sql.vm")) {
			return className.toLowerCase() + "_menu.sql";
		}
		//
		if(template.contains("index.vue.vm")) {
			return "main" + File.separator + "resources" + File.separator + "src" + File.separator + "views" + File.separator + "modules" +
                    File.separator + moduleName + File.separator + className.toLowerCase() + ".vue";
		}
		//
		if(template.contains("add-or-update.vue.vm")) {
			return "main" + File.separator + "resources" + File.separator + "src" + File.separator + "views" + File.separator + "modules" +
                    File.separator + moduleName + File.separator + className.toLowerCase() + "-add-or-update.vue";
		}
		return null;
	}
	
}
