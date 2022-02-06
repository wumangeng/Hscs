package com.hscs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Author Painter
 * @Description 阿里云上传业务启动类
 * @Date 2021/1/26 17:52
 **/

@EnableSwagger2
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan(basePackages = {"com.hscs"})
@EnableDiscoveryClient
public class VideoUploadApplication {
    public static void main(String[] args) {
        SpringApplication.run(VideoUploadApplication.class, args);
    }

}
