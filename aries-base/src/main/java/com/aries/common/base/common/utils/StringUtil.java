package com.aries.common.base.common.utils;

import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Blob;
import java.sql.Clob;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
	
	/**
    *
    * @Title: objectToMap
    * @Description: 将object转换为map，默认保留空值
    * @param @param obj
    * @return Map<String,Object> 返回类型
    * @throws
    */
   public static Map<String, Object> objectToMap(Object obj) {
       Map<String, Object> map = new HashMap<String, Object>();
       map = objectToMap(obj, true);
       return map;
   }

   public static Map<String, Object> objectToMap(Object obj, boolean keepNullVal) {
       if (obj == null) {
           return null;
       }
       Map<String, Object> map = new HashMap<String, Object>();
       try {
           Field[] declaredFields = obj.getClass().getDeclaredFields();
           for (Field field : declaredFields) {
               field.setAccessible(true);
               if (keepNullVal == true) {
                   map.put(field.getName(), field.get(obj));
               } else {
                   if (field.get(obj) != null && !"".equals(field.get(obj).toString())) {
                       map.put(field.getName(), field.get(obj));
                   }
               }
           }
       } catch (Exception e) {
           e.printStackTrace();
       }
       return map;
   }
   
   	/**
   	 * 将结果对象转换成Map
   	 * 
   	 * @param obj	
   	 * @param flag
   	 * @return
   	 */
	@SuppressWarnings("unchecked")
	public static Object objectToMap(Object obj, Boolean flag) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (flag) {
			Map<String, Object> map = (Map<String, Object>) obj;
			for (Entry<String, Object> entry : map.entrySet()) {
				Object temp = entry.getValue();
				try {
					if (temp instanceof Clob) {
						resultMap.put(StringUtil.tablefield2prop(entry.getKey()),
								StringUtil.oracleClob2Str((Clob) temp));
					} else {
						resultMap.put(StringUtil.tablefield2prop(entry.getKey()), temp);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return resultMap;
		} else {
			return obj;
		}
	}
	
	/**
     * 把bean的属性名转成数据库表的字段名
     * 
     * @param field String
     * @return String
     */
    public static String prop2tablefield(String field) {
        if (field == null || "".equals(field))
            return null;
        if (field.matches("[a-z]+[A-Z][a-z]+([A-Z][a-z]+)*")) {
            Pattern pttern = Pattern.compile("[A-Z]");
            Matcher matcher = pttern.matcher(field);
            while (matcher.find()) {
                String old = matcher.group();
                String ne = matcher.group().toLowerCase();

                field = field.replaceAll(old, "_" + ne);
            }
        }
        return field;
    }

    /**
     * 把数据库表的字段名转成bean的属性名
     * 
     * @param column String
     * @return String
     */
    public static String tablefield2prop(String column) {
        if (column == null || "".equals(column)) {
            return "";
        }
        column = column.toLowerCase();
        char[] chars = column.toCharArray();

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (c == '_') {
                int j = i + 1;
                if (j < chars.length) {
                    sb.append(String.valueOf(chars[j]).toUpperCase());
                    i++;
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 把bean的类名转成数据库的表名
     * 
     * @param className 类名
     * @return 表名
     */
    public static String class2table(String className) {
        if (className.matches("[A-Z][a-z]+[A-Z][a-z]+([A-Z][a-z]+)*")) {
            Pattern pttern = Pattern.compile("[A-Z]");
            Matcher matcher = pttern.matcher(className);
            while (matcher.find()) {
                String old = matcher.group();
                String ne = matcher.group().toLowerCase();

                className = className.replaceAll(old, "_" + ne);
            }
        }
        if (className.indexOf("_") == 0)
            className = className.substring(1);
        return className;
    }
    
    /**
    *
    * Description:将Clob对象转换为String对象,Blob处理方式与此相同
    *
    * @param clob 字符大对象
    * @return String对象
    * @throws Exception unknown
    */
   public static String oracleClob2Str(Clob clob) throws Exception {
       return (clob != null ? clob.getSubString(1, (int) clob.length()) : null);
   }
   
   /**
    *
    * Description:将string对象转换为Clob对象,Blob处理方式与此相同
    *
    * @param str 字符串
    * @param lob Clob对象
    * @return Clob对象
    * @throws Exception unknown
    */
   public static Clob oracleStr2Clob(String str, Clob lob) throws Exception {
       Method methodToInvoke = lob.getClass().getMethod(
               "getCharacterOutputStream", (Class[]) null);
       Writer writer = (Writer) methodToInvoke.invoke(lob, (Object[]) null);
       writer.write(str);
       writer.close();
       return lob;
   }
   
   /**
   *
   * Description:将Clob对象转换为String对象,Blob处理方式与此相同
   *
   * @param blob Blob对象
   * @return String对象
   * @throws Exception unknown
   */
  public static byte[] oracleBlob2Str(Blob blob) throws Exception {
      return (blob != null ? blob.getBytes(1, (int) blob.length()) : null);
  }
  
  /**
   *
   * Description:将string对象转换为Clob对象,Blob处理方式与此相同
   *
   * @param str String对象
   * @param blob Blob对象
   * @return Blob
   * @throws Exception unknown
   */
  public static Blob oracleStr2Blob(String str, Blob blob) throws Exception {
      Method methodToInvoke = blob.getClass().getMethod(
              "getCharacterOutputStream", (Class[]) null);
      Writer writer = (Writer) methodToInvoke.invoke(blob, (Object[]) null);
      writer.write(str);
      writer.close();
      return blob;
  }

	
}
