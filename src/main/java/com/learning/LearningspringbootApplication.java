package com.learning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class LearningspringbootApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext applicationContext=SpringApplication.run(LearningspringbootApplication.class, args);
		
	}

}
