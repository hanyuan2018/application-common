package com.aries.common.base.web.controller.index;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.aries.common.base.common.constants.AttrConstants;
import com.aries.common.base.common.constants.ForwardConstants;
import com.aries.common.base.common.utils.SessionUtil;
import com.aries.common.base.web.entity.SysUserEntity;
import com.aries.common.base.web.mapper.SysUserMapper;

@Controller
@RequestMapping("/")
public class BaseBackGroundController extends  BaseController{

    @Autowired
    private SysUserMapper sysUserMapper;

    /**
     * 登陆画面
     *
     * @return
     */
    @RequestMapping(value = "")
    public String toIndex() {
        return ForwardConstants.REDIRECT + ForwardConstants.LOGIN;
    }

    /**
     * 进入登陆画面
     */
    @RequestMapping(value = "login", method = RequestMethod.GET, produces = "text/html; charset=utf-8")
    public String login(HttpServletRequest request) {
        return ForwardConstants.LOGIN;
    }

    /**
     * 用户登录方法
     *
     * @param request
     * @param userName
     * @param password
     * @return
     */
    @RequestMapping(value = "login", method = RequestMethod.POST, produces = "text/html; charset=utf-8")
    public String login(HttpServletRequest request, String userName, String password){
        // 判断用户名与参数是否为空
        if(StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)){
            return ForwardConstants.LOGIN;
        }
        SysUserEntity sysUserEntity = new SysUserEntity();
        sysUserEntity.setUserName(userName);
        sysUserEntity.setPassword(password);
        List<SysUserEntity> userInfo = sysUserMapper.getUserInfo(sysUserEntity);
        if(userInfo.size() == 0){
            request.setAttribute(AttrConstants.ERROR, "用户名或密码不正确");
            return ForwardConstants.LOGIN;
        }
        // 将用户信息存放到Session中
        SysUserEntity sysUser = userInfo.get(0);
        SessionUtil.setUserIdSession(sysUser.getUserId()+"");
        SessionUtil.setUserSession(sysUser);
        return ForwardConstants.REDIRECT + ForwardConstants.INDEX;
    }

    /**
     * 进入主页面
     *
     * @throws Exception
     */
    @RequestMapping("index")
    public String index(Model model) throws Exception {
        SysUserEntity sysUserEntity = (SysUserEntity)SessionUtil.getUserSession();
        model.addAttribute("SysUserInfo", sysUserEntity);
        return ForwardConstants.INDEX;
    }

    @RequestMapping("main")
    public String main() {
        return ForwardConstants.MAIN;
    }
}
