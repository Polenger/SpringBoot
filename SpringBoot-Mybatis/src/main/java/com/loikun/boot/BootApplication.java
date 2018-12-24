package com.loikun.boot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Repository;

/**
 * 启动类
 * @author loikun
 */
@SpringBootApplication
@MapperScan(basePackages = "com.loikun.boot.dao",annotationClass = Repository.class) // 映射文件扫描
public class BootApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootApplication.class);
    }

}
