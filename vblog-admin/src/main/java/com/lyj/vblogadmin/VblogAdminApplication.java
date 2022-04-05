package com.lyj.vblogadmin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.lyj.vblogadmin.mapper")
public class VblogAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(VblogAdminApplication.class, args);
    }

}
