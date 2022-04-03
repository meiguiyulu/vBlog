package com.lyj.vblog.service.impl;

import com.lyj.vblog.pojo.Admin;
import com.lyj.vblog.mapper.AdminMapper;
import com.lyj.vblog.service.IAdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
