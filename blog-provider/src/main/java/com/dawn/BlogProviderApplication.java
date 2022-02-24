package com.dawn;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.dawn.mapper")
@EnableDubboConfiguration
public class BlogProviderApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogProviderApplication.class, args);
	}

}
