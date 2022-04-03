package com.lyj.vblog.utils;

import com.lyj.vblog.pojo.SysUser;

public class ThreadLocalUtil {
    private ThreadLocalUtil() {
    }

    private static final ThreadLocal<SysUser> LOCAL = new ThreadLocal<>();

    public static void put(SysUser user) {
        LOCAL.set(user);
    }

    public static SysUser get() {
        return LOCAL.get();
    }

    public static void remove() {
        LOCAL.remove();
    }
}
