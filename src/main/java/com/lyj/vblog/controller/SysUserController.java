package com.lyj.vblog.controller;


import com.lyj.vblog.common.ErrorCode;
import com.lyj.vblog.common.Result;
import com.lyj.vblog.service.ISysUserService;
import com.lyj.vblog.vo.LoginUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author LiuYunJie
 * @since 2022-04-02
 */
@RestController
@RequestMapping("/users")
public class SysUserController {

    @Autowired
    private ISysUserService userService;

    @GetMapping("/currentUser")
    public Result currentUser(@RequestHeader("Authorization") String token) {
        LoginUserVo user = userService.findUserByToken(token);
        if (user == null) {
            return Result.fail(ErrorCode.NO_LOGIN.getCode(), ErrorCode.NO_LOGIN.getMsg());
        }
        return Result.success(user);
    }
}
