package com.lyj.vblog.service;

import com.lyj.vblog.pojo.Category;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lyj.vblog.vo.CategoryVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author LiuYunJie
 * @since 2022-04-02
 */
public interface ICategoryService extends IService<Category> {

    /**
     * 所有类别
     * @return
     */
    List<CategoryVo> findAll();
}
