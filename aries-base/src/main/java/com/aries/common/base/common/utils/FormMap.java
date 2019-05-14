package com.aries.common.base.common.utils;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

/**
 *
 * @author hanp
 * @param <K>
 * @param <V>
 * SpringMvc 把请求的所有参数封装到Map中,提供最常用的方法
 */
public class FormMap<K, V> extends HashMap<K, V> implements Serializable {

    private static final long serialVersionUID = 1L;

    public void set(K key, V value) {
        this.put(key, value);
    }

    /**
     * Get attribute of mysql type: varchar, char, enum, set, text, tinytext,
     * mediumtext, longtext
     *
     * @param attr 属性名
     * @return 返回值
     */
    public String getStr(String attr) {
        return (String) this.get(attr);
    }

    /**
     * Get attribute of mysql type: int, integer, tinyint(n) n &gt; 1, smallint,
     * mediumint
     *
     * @param attr 属性名
     * @return 返回值
     */
    public Integer getInt(String attr) {
        return (Integer) this.get(attr);
    }

    /**
     * Get attribute of mysql type: bigint, unsign int
     *
     * @param attr 属性名
     * @return 返回值
     */
    public Long getLong(String attr) {
        return (Long) this.get(attr);
    }

    /**
     * Get attribute of mysql type: unsigned bigint
     *
     * @param attr 属性名
     * @return 返回值
     */
    public java.math.BigInteger getBigInteger(String attr) {
        return (java.math.BigInteger) this.get(attr);
    }

    /**
     * Get attribute of mysql type: date, year
     *
     * @param attr 属性名
     * @return 返回值
     */
    public java.util.Date getDate(String attr) {
        return (java.util.Date) this.get(attr);
    }

    /**
     * Get attribute of mysql type: time
     *
     * @param attr 属性名
     * @return 返回值
     */
    public java.sql.Time getTime(String attr) {
        return (java.sql.Time) this.get(attr);
    }

    /**
     * Get attribute of mysql type: timestamp, datetime
     *
     * @param attr 属性名
     * @return 返回值
     */
    public java.sql.Timestamp getTimestamp(String attr) {
        return (java.sql.Timestamp) this.get(attr);
    }

    /**
     * Get attribute of mysql type: real, double
     *
     * @param attr 属性名
     * @return 返回值
     */
    public Double getDouble(String attr) {
        return (Double) this.get(attr);
    }

    /**
     * Get attribute of mysql type: float
     *
     * @param attr 属性名
     * @return 返回值
     */
    public Float getFloat(String attr) {
        return (Float) this.get(attr);
    }

    /**
     * Get attribute of mysql type: bit, tinyint(1)
     *
     * @param attr 属性名
     * @return 返回值
     */
    public Boolean getBoolean(String attr) {
        return (Boolean) this.get(attr);
    }

    /**
     * Get attribute of mysql type: decimal, numeric
     *
     * @param attr 属性名
     * @return 返回值
     */
    public java.math.BigDecimal getBigDecimal(String attr) {
        return (java.math.BigDecimal) this.get(attr);
    }

    /**
     * Get attribute of mysql type: binary, varbinary, tinyblob, blob,
     * mediumblob, longblob
     *
     * @param attr 属性名
     * @return 返回值
     */
    public byte[] getBytes(String attr) {
        return (byte[]) this.get(attr);
    }

    /**
     * Get attribute of any type that extends from Number
     *
     * @param attr 属性名
     * @return 返回值
     */
    public Number getNumber(String attr) {
        return (Number) this.get(attr);
    }

    /**
     * Return attribute names of this model.
     * @return 返回属性名
     */
    public String[] getAttrNames() {
        Set<K> attrNameSet = this.keySet();
        return attrNameSet.toArray(new String[attrNameSet.size()]);
    }

    /**
     * Return attribute values of this model.
     * @return 返回属性名
     */
    public Object[] getAttrValues() {
        Collection<V> attrValueCollection = values();
        return attrValueCollection.toArray(new Object[attrValueCollection.size()]);
    }

}
