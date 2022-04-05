package com.lyj.vblogadmin.service.impl;

import com.lyj.vblogadmin.pojo.Admin;
import com.lyj.vblogadmin.mapper.AdminMapper;
import com.lyj.vblogadmin.service.IAdminService;
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
