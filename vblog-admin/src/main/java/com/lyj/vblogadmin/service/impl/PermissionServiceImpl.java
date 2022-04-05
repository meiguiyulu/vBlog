package com.lyj.vblogadmin.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.db.PageResult;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyj.vblogadmin.params.PageParam;
import com.lyj.vblogadmin.params.pageResult;
import com.lyj.vblogadmin.pojo.Permission;
import com.lyj.vblogadmin.mapper.PermissionMapper;
import com.lyj.vblogadmin.service.IPermissionService;
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
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    /**
     * 分页查询
     * @param pageParam
     * @return
     */
    @Override
    public pageResult<Permission> listPermission(PageParam pageParam) {
        Page<Permission> page = new Page<>(pageParam.getCurrentPage(), pageParam.getPageSize());
        QueryWrapper<Permission> wrapper = new QueryWrapper<Permission>();
        if (!StrUtil.isBlank(pageParam.getQueryString())) {
            wrapper.eq("name", pageParam.getQueryString());
        }
        Page<Permission> permissionPage = permissionMapper.selectPage(page, wrapper);
        pageResult<Permission> result = new pageResult<>();
        result.setList(permissionPage.getRecords());
        result.setTotal(permissionPage.getTotal());
        return result;
    }

    /**
     * 新加
     * @param permission
     * @return
     */
    @Override
    public boolean addPermission(Permission permission) {
        permissionMapper.insert(permission);
        return true;
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @Override
    public Boolean delete(Long id) {
        permissionMapper.deleteById(id);
        return null;
    }
}
