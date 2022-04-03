package com.lyj.vblog.service.impl;

import com.lyj.vblog.pojo.SysUser;
import com.lyj.vblog.mapper.SysUserMapper;
import com.lyj.vblog.service.ISysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Autowired
    private SysUserMapper userMapper;

    @Override
    public SysUser findUserById(long userId) {
        SysUser user = userMapper.selectById(userId);
        if (user == null) {
            user = new SysUser();
            user.setNickname("云小杰");
        }
        return user;
    }
}
