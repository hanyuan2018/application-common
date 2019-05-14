package com.aries.common.base.web.entity;

import lombok.Data;

import java.sql.Blob;
import java.sql.Timestamp;

/**
 * @author hanp
 *
 * 用户表对应的实体类
 */
@Data
public class SysUserEntity {
    // 用户ID
    private Integer userId;
    // 姓名
    private String name;
    // 用户名
    private String userName;
    // 密码
    private String password;
    // 身份证号
    private String sfzh;
    // 性别 0-男；1-女
    private String sex;
    // 手机号码
    private String phone;
    // 邮箱
    private String email;
    // 头像信息
    private Blob avatar;
    // 备注信息
    private String comment;
    // 锁死状态 0-有效；1-锁死
    private String lockFlag;
    // 逻辑删除状态0:存在1:删除
    private String deleteFlag;
    // 创建用户
    private Integer createUser;
    // 创建时间
    private Timestamp createTime;
    // 更新用户
    private Integer updateUser;
    // 更新时间
    private Timestamp updateTime;

}
