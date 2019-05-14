package com.aries.common.base.web.service.impl;

import com.aries.common.base.web.entity.SysUserEntity;
import com.aries.common.base.web.mapper.SysUserMapper;
import com.aries.common.base.web.service.SysUserServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *  用户表 service 实现类
 *
 * @author hanp
 *
 */
@Service
public class SysUserServiceImpl implements SysUserServiceI {

    @Autowired
    public SysUserMapper sysUserMapper;

    @Override
    public List<SysUserEntity> getUserInfo(SysUserEntity sysUserEntity) {

        List<SysUserEntity> sysUserList = sysUserMapper.getUserInfo(sysUserEntity);

        return null;
    }
}
