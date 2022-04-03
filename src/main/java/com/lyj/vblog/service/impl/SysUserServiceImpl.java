package com.lyj.vblog.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.MD5;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lyj.vblog.common.ErrorCode;
import com.lyj.vblog.common.Result;
import com.lyj.vblog.pojo.SysUser;
import com.lyj.vblog.mapper.SysUserMapper;
import com.lyj.vblog.service.ISysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyj.vblog.utils.JWTUtil;
import com.lyj.vblog.utils.SaltUtils;
import com.lyj.vblog.vo.LoginParam;
import com.lyj.vblog.vo.LoginUserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;
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
@Transactional
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

    /**
     * 根据account查询用户信息
     *
     * @param account
     * @return
     */
    @Override
    public SysUser findUserByAccount(String account) {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<SysUser>()
                .eq("account", account);
        SysUser user = userMapper.selectOne(wrapper);
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

        // 修改用户上次登陆时间
        user.setLastLogin(new Date());
        userMapper.updateById(user);

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

    /**
     * 根据token查询登录用户的信息
     *
     * @param token
     * @return
     */
    @Override
    public LoginUserVo findUserByToken(String token) {
        /**
         * 1. 校验token合法性
         *      是否为空/解析是否成功/redis是否存在
         * 2. 如果校验失败，返回错误
         * 3. 如果成功，返回对应的结果
         */
        if (token == null) {
            return null;
        }
        SysUser sysUser = checkToken(token);
        LoginUserVo loginUserVo = new LoginUserVo();
        BeanUtils.copyProperties(sysUser, loginUserVo);

        return loginUserVo;
    }

    @Override
    public SysUser checkToken(String token) {
        if (StrUtil.isBlank(token)) {
            return null;
        }
        Map<String, Object> map = JWTUtil.checkToken(token);
        if (map == null) {
            return null;
        }
        String s = redisTemplate.opsForValue().get("TOKEN_" + token);
        if (StrUtil.isBlank(s)) {
            return null;
        }
        SysUser user = JSONUtil.toBean(s, SysUser.class);
        return user;
    }

    /**
     * 注销
     *
     * @param token
     * @return
     */
    @Override
    public Object logout(String token) {
        redisTemplate.delete("TOKEN_" + token);
        return null;
    }

    /**
     * 注册
     *
     * @param loginParam
     * @return
     */
    @Override
    public Result register(LoginParam loginParam) {
        /**
         * 1. 判断参数是否合法
         * 2. 判断账户是否存在
         *          若存在则返回账户以及被注册；
         *          不存在则注册用户, 生成token，加入redis
         * 3. 开启事务
         */
        String account = loginParam.getAccount();
        String password = loginParam.getPassword();
        String nickname = loginParam.getNickname();
        if (StrUtil.isBlank(account) || StrUtil.isBlank(password) || StrUtil.isBlank(nickname)) {
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.PARAMS_ERROR.getMsg());
        }

        SysUser user = findUserByAccount(account);
        if (user != null) {
            return Result.fail(ErrorCode.ACCOUNT_EXIST.getCode(), ErrorCode.ACCOUNT_PWD_NOT_EXIST.getMsg());
        }

        user = new SysUser();
        user.setAccount(account);
        user.setAdmin(1); //1 为true
        user.setAvatar("/static/img/avatar.png");
        user.setCreateDate(new Date());
        user.setDeleted(0); // 0 为false
        user.setEmail("");
        user.setLastLogin(new Date());
//        user.setMobilePhoneNumber();
        user.setNickname(nickname);
        user.setPassword(SecureUtil.md5(password + slat));
        user.setSalt(SaltUtils.getSalt(6));
        user.setStatus("");
        userMapper.insert(user);

        user = findUserByAccount(account);
        String token = JWTUtil.createToken(user.getId());
        redisTemplate.opsForValue().set("TOKEN_" + token, JSONUtil.toJsonStr(user), 1, TimeUnit.DAYS);
        return Result.success(token);
    }
}
