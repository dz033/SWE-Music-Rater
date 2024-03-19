package com.musicrater.MusicRater;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class MusicRaterApplication {

	public static void main(String[] args) {
		SpringApplication.run(MusicRaterApplication.class, args);
	}

}