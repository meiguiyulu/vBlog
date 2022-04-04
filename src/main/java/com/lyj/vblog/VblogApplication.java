package com.lyj.vblog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@MapperScan("com.lyj.vblog.mapper")
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class VblogApplication {

    public static void main(String[] args) {
        SpringApplication.run(VblogApplication.class, args);
    }

}
