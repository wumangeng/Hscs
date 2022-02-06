package com.hscs;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Author Painter
 * @Description TODO
 * @Date 2021/3/3 18:25
 **/

@EnableSwagger2
@SpringBootApplication
@ComponentScan(basePackages = {"com.hscs"})
@MapperScan("com.hscs.mapper")
@EnableDiscoveryClient
public class InfopushApplication {
    public static void main(String[] args) {
        SpringApplication.run(InfopushApplication.class, args);
    }
}
