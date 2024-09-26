package com.springboot.crud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.springboot.crud")
public class SpringBootRestCrud1Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootRestCrud1Application.class, args);
	}

}
