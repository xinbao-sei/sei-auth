package com.changhong.sei.auth.certification.sso;

import com.changhong.sei.auth.certification.TokenAuthenticator;
import com.changhong.sei.auth.dto.LoginRequest;
import com.changhong.sei.auth.dto.SessionUserResponse;
import com.changhong.sei.core.dto.ResultData;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-04-23 12:16
 */
public interface Oauth2Authenticator extends SingleSignOnAuthenticator {

    /**
     * 前端web根url地址.必须
     * 如:http://tsei.changhong.com:8090/sei-portal-web
     */
    String getWebBaseUrl();
    /**
     * APP根url地址.必须
     * 如:http://tsei.changhong.com:8090/sei-app
     */
    String getAppBaseUrl();
    /**
     * 服务网关根url地址
     * 如:http://tsei.changhong.com:8090/sei-app
     */
    String getApiBaseUrl();
    /**
     * 登录成功url地址
     */
    String getIndexUrl();

    /**
     * 登录失败url地址
     */
    String getLogoutUrl();

    /**
     * oauth2授权路由端点
     */
    String getAuthorizeEndpoint(HttpServletRequest request);

    ResultData<Map<String, String>> getAuthorizeData(HttpServletRequest request);

    /**
     * 绑定账号
     */
    ResultData<SessionUserResponse> bindingAccount(LoginRequest loginRequest,HttpServletRequest request );

    /**
     * 获取用户信息
     */
    ResultData<SessionUserResponse> auth(HttpServletRequest request);

    ResultData<Map<String, String>> jsapi_ticket();
}
