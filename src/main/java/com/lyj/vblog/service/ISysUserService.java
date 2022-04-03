package com.lyj.vblog.service;

import com.lyj.vblog.common.Result;
import com.lyj.vblog.pojo.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lyj.vblog.vo.LoginParam;
import com.lyj.vblog.vo.LoginUserVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author LiuYunJie
 * @since 2022-04-02
 */
public interface ISysUserService extends IService<SysUser> {

    /**
     * 根据Id查询作者信息
     * @param userId
     * @return
     */
    SysUser findUserById(long userId);

    /**
     * 根据account查询用户信息
     * @param account
     * @return
     */
    SysUser findUserByAccount(String account);

    /**
     * 登录功能
     * @param loginParam
     * @return
     */
    Result login(LoginParam loginParam);

    SysUser findUser(String account, String password);

    /**
     * 根据token查询登录的用户信息
     * @param token
     * @return
     */
    LoginUserVo findUserByToken(String token);

    /**
     * 退出登录
     * @param token
     * @return
     */
    Object logout(String token);

    /**
     * 注册
     * @param loginParam
     * @return
     */
    Result register(LoginParam loginParam);
}
