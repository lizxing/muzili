package com.lizxing.muzili;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan({"com.lizxing.muzili.module.sys.dao"})
public class MuziliApplication {

    public static void main(String[] args) {
        SpringApplication.run(MuziliApplication.class, args);
    }

}
