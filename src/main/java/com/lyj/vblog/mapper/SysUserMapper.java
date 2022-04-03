package com.lyj.vblog.mapper;

import com.lyj.vblog.pojo.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author LiuYunJie
 * @since 2022-04-02
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

}
