package com.lyj.vblogadmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lyj.vblogadmin.mapper.PermissionMapper;
import com.lyj.vblogadmin.pojo.Admin;
import com.lyj.vblogadmin.mapper.AdminMapper;
import com.lyj.vblogadmin.pojo.Permission;
import com.lyj.vblogadmin.service.IAdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author LiuYunJie
 * @since 2022-04-02
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public Admin findAdminBuUserName(String username) {
        QueryWrapper<Admin> wrapper = new QueryWrapper<Admin>()
                .eq("username", username)
                .last("limit 1"); // 为了优化效率
        Admin admin = adminMapper.selectOne(wrapper);
        return admin;
    }

    @Override
    public List<Permission> findPermissionsByAdminId(Long adminId) {
        return permissionMapper.findPermissionsByAdminId(adminId);
    }
}
