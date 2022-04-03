package com.lyj.vblog.service;

import com.lyj.vblog.pojo.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

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

}
