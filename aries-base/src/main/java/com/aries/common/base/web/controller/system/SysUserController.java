package com.aries.common.base.web.controller.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aries.common.base.web.entity.SysUserEntity;
import com.aries.common.base.web.entity.SysUserFormMap;
import com.aries.common.base.web.mapper.SysUserMapper;

/**
 *  用户表实现控制类
 *
 * @author hanp
 */
@Controller
@RequestMapping("/api/sysUser/")
public class SysUserController {

	@Autowired
	private SysUserMapper sysUserMapper;
	
	@ResponseBody
	@RequestMapping("getUserList")
	public List<SysUserEntity> getUserList(){
		//List<SysUserFormMap> userInfo = sysUserMapper.getUserInfo(new SysUserFormMap());
		List<SysUserEntity> userInfo = sysUserMapper.getUserList(new SysUserEntity());
		return userInfo;
	}
	
	@ResponseBody
	@RequestMapping("getUserInfo")
	public List<SysUserFormMap> getUserInfo(){
		List<SysUserFormMap> userInfo = sysUserMapper.getUserInfo(new SysUserFormMap());
		return userInfo;
	}
	
}
