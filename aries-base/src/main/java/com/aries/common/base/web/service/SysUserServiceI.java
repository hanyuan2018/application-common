package com.aries.common.base.web.service;

import com.aries.common.base.web.entity.SysUserEntity;

import java.util.List;

public interface SysUserServiceI {

    //  根据参数获取用户信息
    List<SysUserEntity> getUserInfo(SysUserEntity sysUserEntity);

}
