package com.aries.common.base.common.utils;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Enumeration;

/**
 * @Description 对于Session中的操作，封装起来，因为键值对中的键是String类型，不便于控制，在这里可以隐藏细节
 * @author hanp
 */
public class SessionUtil {

    // 本地异常日志记录对象
    private static Logger logger = LoggerFactory.getLogger(SessionUtil.class);

    // 登录帐号的SessionKey
    public static final String SESSION_USER_KEY = "userSession";
    //登录帐号ID的SessionKey
    public static final String SESSION_USER_ID_KEY = "userIdSession";
    //登陆账号权限的SessionKey
    public static final String SESSION_USER_ACCESS_TOKEN_KEY = "accessToken";

    // 获取一个session对象
    //private static HttpSession session = getSession();

    /**
     * 获取一个request对象的方法
     *
     * @return HttpServletRequest
     */
    public static HttpServletRequest getRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        return request;
    }

    /**
     * 获取session 的方法
     *
     * @return HttpSession
     */
    public static HttpSession getSession() {
        HttpSession session = null;
        try {
            session = getRequest().getSession();
        } catch (Exception e) {
        }
        return session;
    }

    /**
     * 获取登录账号的ID
     *
     * @return userid
     */
    public static String getUserIdSession() {
        Object obj = getSession().getAttribute(SESSION_USER_ID_KEY);
        if (obj != null) {
            return obj.toString();
        }
        return null;
    }

    /**
     * 获取登录账号的的对象
     *
     * @return Object 返回是Object..需要转型为(Account)Object
     */
    public static Object getUserSession() {
        return (Object) getSession().getAttribute(SESSION_USER_KEY);
    }

    /**
     * 获取登录账号的的凭证
     *
     */
    public static String getAccessTokenSession() {
        return (String) getSession().getAttribute(SESSION_USER_ACCESS_TOKEN_KEY);
    }

    /**
     * 设定登录账号的用户ID到session中
     *
     * @param userId 登录账号的用户ID
     */
    public static void setUserIdSession(String userId) {
        getSession().setAttribute(SESSION_USER_ID_KEY, userId);
    }

    /**
     * 设定登录账号的对象到session中
     *
     * @param object Object对象
     */
    public static void setUserSession(Object object) {
        getSession().setAttribute(SESSION_USER_KEY, object);
    }

    /**
     * 设定登录账号的权限到session中
     *
     * @param accessToken token
     */
    public static void setAccessTokenSession(String accessToken) {
        getSession().setAttribute(SESSION_USER_ACCESS_TOKEN_KEY, accessToken);
    }

    /**
     * 清楚session中的登录账号的用户ID信息
     */
    public static void removeUserIdSession() {
        getSession().removeAttribute(SESSION_USER_ID_KEY);
    }

    /**
     * 清楚session中的登录账号的对象信息
     */
    public static void removeUserSession() {
        getSession().removeAttribute(SESSION_USER_KEY);
    }

    /**
     * 清楚session中的登录账号的凭证信息
     */
    public static void removeAccessTokenSession() {
        getSession().removeAttribute(SESSION_USER_ACCESS_TOKEN_KEY);
    }

    /**
     * 获取页面传递的某一个参数值,
     * @param key String
     * @return String
     */
    public static String getPara(String key) {
        return getRequest().getParameter(key);
    }

    /**
     * 获取页面传递的某一个数组值,
     * @param key String
     * @return String
     */
    public static String[] getParaValues(String key) {
        String[] params = getRequest().getParameterValues(key);
        if (params != null) return params;
        return new String[]{getRequest().getParameter(key.substring(0, key.length()-2))};
    }

    /**
     * 获取Request参数值,
     * @param key String
     * @return Object对象
     */
    public static Object getRequestAttr(String key) {
        return getRequest().getAttribute(key);
    }

    /**
     * 设定Request参数值,
     * @param key String
     * @param obj Object对象
     */
    public static void setRequestAttr(String key, Object obj) {
        getRequest().setAttribute(key, obj);
    }

    /**
     * 获取Session参数值,
     * @param key String
     * @return Object对象
     */
    public static Object getSessionAttr(String key) {
        return getSession().getAttribute(key);
    }

    /**
     * 设定Session参数值,
     * @param key String
     * @param obj Object对象
     */
    public static void setSessionAttr(String key, Object obj) {
        getSession().setAttribute(key, obj);
    }

    /**
     * 清楚Session参数值,
     * @param key String
     */
    public static void removeSessionAttr(String key) {
        getSession().removeAttribute(key);
    }

    /**
     * 获取传递的所有参数, 反射实例化对象，再设置属性值 通过泛型回传对象.
     *
     * @return JSONObject
     */
    public static JSONObject getJsonParam() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        Enumeration<String> en = request.getParameterNames();
        JSONObject object = new JSONObject();
        try {
            while (en.hasMoreElements()) {
                String nms = en.nextElement().toString();
                if (nms.endsWith("[]")) {
                    String[] as = request.getParameterValues(nms);
                    object.put(nms, as);
                } else {
                    String as = request.getParameter(nms);
                    // 画面输入为空时 丢失
                    object.put(nms, as);
                }
            }
        } catch (Exception e) {
            logger.error("getJsonParam method error --> ", e);
        }
        return object;
    }

    /**
     * 获取客户端IP地址,
     * @return String
     * @throws IOException IO异常
     */
    public static String getIpAddress() throws IOException {
        return getIpAddress(getRequest());
    }

    /**
     * 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址;
     *
     * @param request HttpServletRequest对象
     * @return String
     * @throws IOException IO异常
     */
    public final static String getIpAddress(HttpServletRequest request) throws IOException {
        // 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址
        String ip = request.getHeader("X-Forwarded-For");
        if (logger.isInfoEnabled()) {
            logger.info("getIpAddress(HttpServletRequest) - X-Forwarded-For - String ip=" + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
            if (logger.isInfoEnabled()) {
                logger.info("getIpAddress(HttpServletRequest) - Proxy-Client-IP - String ip=" + ip);
            }
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
            if (logger.isInfoEnabled()) {
                logger.info("getIpAddress(HttpServletRequest) - WL-Proxy-Client-IP - String ip=" + ip);
            }
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            if (logger.isInfoEnabled()) {
                logger.info("getIpAddress(HttpServletRequest) - HTTP_X_FORWARDED_FOR - String ip=" + ip);
            }
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED");
            if (logger.isInfoEnabled()) {
                logger.info("getIpAddress(HttpServletRequest) - HTTP_X_FORWARDED - String ip=" + ip);
            }
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_CLUSTER_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
            if (logger.isInfoEnabled()) {
                logger.info("getIpAddress(HttpServletRequest) - HTTP_CLIENT_IP - String ip=" + ip);
            }
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_FORWARDED_FOR");
            if (logger.isInfoEnabled()) {
                logger.info("getIpAddress(HttpServletRequest) - HTTP_FORWARDED_FOR - String ip=" + ip);
            }
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_FORWARDED");
            if (logger.isInfoEnabled()) {
                logger.info("getIpAddress(HttpServletRequest) - HTTP_FORWARDED - String ip=" + ip);
            }
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_VIA");
            if (logger.isInfoEnabled()) {
                logger.info("getIpAddress(HttpServletRequest) - HTTP_VIA - String ip=" + ip);
            }
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("REMOTE_ADDR");
            if (logger.isInfoEnabled()) {
                logger.info("getIpAddress(HttpServletRequest) - REMOTE_ADDR - String ip=" + ip);
            }
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            if (logger.isInfoEnabled()) {
                logger.info("getIpAddress(HttpServletRequest) - getRemoteAddr - String ip=" + ip);
            }
        }
        return ip;
    }

}
