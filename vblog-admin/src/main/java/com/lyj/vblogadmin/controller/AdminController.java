package com.lyj.vblogadmin.controller;


import com.lyj.vblogadmin.common.Result;
import com.lyj.vblogadmin.params.PageParam;
import com.lyj.vblogadmin.pojo.Permission;
import com.lyj.vblogadmin.service.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author LiuYunJie
 * @since 2022-04-02
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private IPermissionService permissionService;

    /**
     * 分页查询
     *
     * @param pageParam
     * @return
     */
    @PostMapping("permission/permissionList")
    public Result permissionList(@RequestBody PageParam pageParam) {
        return Result.success(permissionService.listPermission(pageParam));
    }

    /**
     * 新加
     *
     * @param permission
     * @return
     */
    @PostMapping("/permission/add")
    public Result addPermission(@RequestBody Permission permission) {
        return Result.success(permissionService.addPermission(permission));
    }

    @PostMapping("/permission/update")
    public Result update(@RequestBody Permission permission) {
        return Result.success(permissionService.updateById(permission));
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @GetMapping("/permission/delete/{id}")
    public Result delete(@PathVariable("id") Long id) {
        return Result.success(permissionService.delete(id));
    }

}
