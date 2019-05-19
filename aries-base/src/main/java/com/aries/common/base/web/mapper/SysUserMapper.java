package com.aries.common.base.web.mapper;

import com.aries.common.base.web.entity.SysUserEntity;
import com.aries.common.base.web.entity.SysUserFormMap;

import java.util.List;

public interface SysUserMapper {

    //  根据参数获取用户信息
    List<SysUserEntity> getUserList(SysUserEntity sysUserEntity);
    
    List<SysUserFormMap> getUserInfo(SysUserFormMap sysUserFormMap);
    
    List<SysUserEntity> getUserInfo(SysUserEntity sysUserEntity);

}
