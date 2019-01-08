package com.trotyzyq.common;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.trotyzyq.*"})
public class CommonComponentApplication {

	public static void main(String[] args) {
		SpringApplication.run(CommonComponentApplication.class, args);
	}
}
