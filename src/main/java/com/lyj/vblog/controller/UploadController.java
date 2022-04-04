package com.lyj.vblog.controller;

import cn.hutool.core.util.StrUtil;
import com.lyj.vblog.common.Result;
import com.lyj.vblog.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/upload")
public class UploadController {

    @Autowired
    private QiniuUtils qiniuUtils;

    @PostMapping
    public Result upload(@RequestParam("image") MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        String filename = UUID.randomUUID().toString() + "."
                + StrUtil.subAfter(originalFilename, ".", true);
        boolean upload = qiniuUtils.upload(file, filename);
        if (upload) {
            return Result.success(QiniuUtils.url + filename);
        }
        return Result.fail(20001, "上传失败");
    }
}
