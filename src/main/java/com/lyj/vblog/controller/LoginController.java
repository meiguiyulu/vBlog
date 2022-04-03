package com.lyj.vblog.controller;

import com.lyj.vblog.common.Result;
import com.lyj.vblog.service.ISysUserService;
import com.lyj.vblog.vo.LoginParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private ISysUserService userService;

    /**
     * 登录功能
     * @param loginParam
     * @return
     */
    @PostMapping
    public Result login(@RequestBody LoginParam loginParam) {
        return userService.login(loginParam);
    }
}
