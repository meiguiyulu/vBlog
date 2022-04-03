package com.lyj.vblog.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.MD5;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lyj.vblog.common.ErrorCode;
import com.lyj.vblog.common.Result;
import com.lyj.vblog.pojo.SysUser;
import com.lyj.vblog.mapper.SysUserMapper;
import com.lyj.vblog.service.ISysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyj.vblog.utils.JWTUtil;
import com.lyj.vblog.vo.LoginParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author LiuYunJie
 * @since 2022-04-02
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    private static final String slat = "mszlu!@###";

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public SysUser findUserById(long userId) {
        SysUser user = userMapper.selectById(userId);
        if (user == null) {
            user = new SysUser();
            user.setNickname("云小杰");
        }
        return user;
    }

    @Override
    public Result login(LoginParam loginParam) {
        /**
         * 1. 检查参数是否合法
         * 2. 根据用户名和密码去user表中查询
         * 3. 如果不存在 登陆失败
         * 4. 如果存在 使用JWT生成token，返回给前端
         * 5. token放入redis中
         * 登录的时候先认证token字符串是否合法
         */
        String account = loginParam.getAccount();
        String password = loginParam.getPassword();
        if (StrUtil.isBlank(account) || StrUtil.isBlank(password)) {
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.PARAMS_ERROR.getMsg());
        }

        String pwd = SecureUtil.md5(password + slat);
        SysUser user = findUser(account, pwd);
        if (user == null) {
            return Result.fail(ErrorCode.ACCOUNT_PWD_NOT_EXIST.getCode(), ErrorCode.ACCOUNT_PWD_NOT_EXIST.getMsg());
        }

        // 登陆成功，使用JWT生成token 存入redis中
        String token = JWTUtil.createToken(user.getId());
        redisTemplate.opsForValue().set("TOKEN_" + token, JSONUtil.toJsonStr(user), 1, TimeUnit.DAYS);
        return Result.success(token);
    }

    @Override
    public SysUser findUser(String account, String password) {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<SysUser>()
                .eq("account", account)
                .eq("password", password);
        SysUser user = userMapper.selectOne(wrapper);
        return user;
    }
}
