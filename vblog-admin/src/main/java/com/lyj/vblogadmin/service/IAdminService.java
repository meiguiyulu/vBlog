package com.lyj.vblogadmin.service;

import com.lyj.vblogadmin.pojo.Admin;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lyj.vblogadmin.pojo.Permission;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author LiuYunJie
 * @since 2022-04-02
 */
public interface IAdminService extends IService<Admin> {

    public Admin findAdminBuUserName(String username);

    public List<Permission> findPermissionsByAdminId(Long adminId);

}
