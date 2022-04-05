package com.lyj.vblogadmin.service;

import com.lyj.vblogadmin.pojo.Admin;
import com.lyj.vblogadmin.pojo.Permission;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@Service
public class AuthService {

    @Autowired
    private IAdminService adminService;

    public boolean auth(HttpServletRequest request, Authentication authentication) {
        // 请求路径
        String requestURI = request.getRequestURI();
        log.info("request url: {}", requestURI);
        // true表示放行; false代表拦截
        Object principal = authentication.getPrincipal();
        if (principal == null || "anonymousUser".equals(principal)) {
            //未登录
            return false;
        }
        UserDetails userDetail = (UserDetails) principal;
        String username = userDetail.getUsername();
        Admin admin = adminService.findAdminBuUserName(username);
        if (admin == null) {
            return false;
        }
        if (admin.getId() == 1 || admin.getId() == 3) {
            // 认为是超级用户
            return true;
        }
        List<Permission> permissions = adminService.findPermissionsByAdminId(admin.getId());
        requestURI = StringUtils.split(requestURI, "?")[0];
        for (Permission permission : permissions) {
            if (requestURI.equals(permission.getPath())) {
                log.info("权限通过");
                return true;
            }
        }
        return false;
    }
}
