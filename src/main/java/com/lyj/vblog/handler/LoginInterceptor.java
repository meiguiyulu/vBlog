package com.lyj.vblog.handler;

import cn.hutool.json.JSONUtil;
import com.lyj.vblog.common.ErrorCode;
import com.lyj.vblog.common.Result;
import com.lyj.vblog.pojo.SysUser;
import com.lyj.vblog.service.ISysUserService;
import com.lyj.vblog.utils.ThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private ISysUserService userService;

    /*执行controller方法之前执行*/
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /**
         * 1. 判断请求的接口是否为 HandlerMethod 即controller方法
         * 2. 判断token是否为空 空表示未登录
         * 3. 如果token不为空则验证token
         * 4. token认证成功 放行
         */
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        String token = request.getHeader("Authorization");
        log.info("=================request start===========================");
        String requestURI = request.getRequestURI();
        log.info("request uri:{}", requestURI);
        log.info("request method:{}", request.getMethod());
        log.info("token:{}", token);
        log.info("=================request end===========================");
        if (token == null) {
            Result result = Result.fail(ErrorCode.NO_LOGIN.getCode(), "未登录");
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSONUtil.toJsonStr(result));
            return false;
        }

        SysUser sysUser = userService.checkToken(token);
        if (sysUser == null) {
            Result result = Result.fail(ErrorCode.NO_LOGIN.getCode(), "未登录");
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSONUtil.toJsonStr(result));
            return false;
        }
        //是登录状态，放行
        //我希望在controller中 直接获取用户的信息 怎么获取?
        ThreadLocalUtil.put(sysUser);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 防止内存泄露
        ThreadLocalUtil.remove();
    }
}
