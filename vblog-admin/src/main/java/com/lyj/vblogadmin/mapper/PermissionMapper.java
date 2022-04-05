package com.lyj.vblogadmin.mapper;

import com.lyj.vblogadmin.pojo.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author LiuYunJie
 * @since 2022-04-02
 */
@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {

    List<Permission> findPermissionsByAdminId(Long adminId);
}
