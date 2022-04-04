package com.lyj.vblog.params;

import com.lyj.vblog.vo.CategoryVo;
import com.lyj.vblog.vo.TagVo;
import lombok.Data;

import java.util.List;

@Data
public class ArticleParam {

    private Long id;

    private ArticleBodyParam body;

    private CategoryVo category;

    private String summary;

    private List<TagVo> tags;

    private String title;
}