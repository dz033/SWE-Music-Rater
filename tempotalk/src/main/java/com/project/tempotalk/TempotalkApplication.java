package com.project.tempotalk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class TempotalkApplication {
	public static void main(String[] args) {
		SpringApplication.run(TempotalkApplication.class, args);
	}

}