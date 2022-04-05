package com.lyj.vblogadmin.service;

import cn.hutool.db.PageResult;
import com.lyj.vblogadmin.params.PageParam;
import com.lyj.vblogadmin.params.pageResult;
import com.lyj.vblogadmin.pojo.Permission;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.data.domain.PageRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author LiuYunJie
 * @since 2022-04-02
 */
public interface IPermissionService extends IService<Permission> {

    /**
     * 分页查询
     * @param pageParam
     * @return
     */
    pageResult<Permission> listPermission(PageParam pageParam);

    /**
     * 新加
     * @param permission
     * @return
     */
    boolean addPermission(Permission permission);

    /**
     * 删除
     * @param id
     * @return
     */
    Boolean delete(Long id);
}
