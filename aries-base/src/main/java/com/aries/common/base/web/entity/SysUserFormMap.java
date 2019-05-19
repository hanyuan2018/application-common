package com.aries.common.base.web.entity;

import com.aries.common.base.common.utils.FormMap;

/**
 * @author hanyuan
 */
public class SysUserFormMap extends FormMap<String, Object> {

    private static final long serialVersionUID = 1L;

    private static final String TABLE_NAME = "SYS_USER";
    // 设置表名称
    static {
    	setTableName(TABLE_NAME);
    }
}
