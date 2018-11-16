package com.x;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.x.dao")
@SpringBootApplication(scanBasePackages = "com.x")
public class GenesisApplication {

	public static void main(String[] args) {
		SpringApplication.run(GenesisApplication.class, args);
	}
}
