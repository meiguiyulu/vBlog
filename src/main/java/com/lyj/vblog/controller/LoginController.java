package com.lyj.vblog.controller;

import com.lyj.vblog.common.Result;
import com.lyj.vblog.service.ISysUserService;
import com.lyj.vblog.params.LoginParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {

    @Autowired
    private ISysUserService userService;

    /**
     * 登录功能
     *
     * @param loginParam
     * @return
     */
    @PostMapping("/login")
    public Result login(@RequestBody LoginParam loginParam) {
        return userService.login(loginParam);
    }

    /**
     * 注销
     */
    @GetMapping("/logout")
    public Result logout(@RequestHeader("Authorization") String token) {
        return Result.success(userService.logout(token));
    }

    /**
     * 注册
     *
     * @param loginParam
     * @return
     */
    @PostMapping("/register")
    public Result register(@RequestBody LoginParam loginParam) {
        return userService.register(loginParam);
    }
}
