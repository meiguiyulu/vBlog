package com.lyj.vblog.service.impl;

import com.lyj.vblog.pojo.Category;
import com.lyj.vblog.mapper.CategoryMapper;
import com.lyj.vblog.service.ICategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyj.vblog.vo.CategoryVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author LiuYunJie
 * @since 2022-04-02
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements ICategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 所有类别
     * @return
     */
    @Override
    public List<CategoryVo> findAll() {
        List<Category> list = categoryMapper.selectList(null);
        List<CategoryVo> categoryVos = copyList(list);
        return categoryVos;
    }

    private List<CategoryVo> copyList(List<Category> categories) {
        List<CategoryVo> categoryVos = new ArrayList<>();
        for (Category category : categories) {
            CategoryVo vo = new CategoryVo();
            BeanUtils.copyProperties(category, vo);
            categoryVos.add(vo);
        }
        return categoryVos;
    }
}
