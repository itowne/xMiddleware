package com.open.item.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * spring-boot 入口
 * 
 * @author towne
 * @date Sep 14, 2018
 */

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages = { "com.open.item.*" })
public class ApplicationController {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationController.class, args);
    }
}
