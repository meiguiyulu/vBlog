package com.lyj.vblog;

import cn.hutool.crypto.SecureUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class VblogApplicationTests {

    private static final String slat = "mszlu!@###";

    @Test
    void contextLoads() {
        System.out.println(SecureUtil.md5("123" + slat));
    }

}
