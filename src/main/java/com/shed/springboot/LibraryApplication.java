package com.shed.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * SpringBoot应用启动器
 */
@SpringBootApplication
@EnableTransactionManagement // 开启事务管理
public class LibraryApplication {
    public static void main(String[] args) {
        SpringApplication.run(LibraryApplication.class,args);
    }
}
