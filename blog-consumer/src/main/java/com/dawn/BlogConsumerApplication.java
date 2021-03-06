package com.dawn;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubboConfiguration
public class BlogConsumerApplication {

	public static void main(String[] args) {

		SpringApplication.run(BlogConsumerApplication.class, args);
	}

}
