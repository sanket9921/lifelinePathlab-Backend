package com.lifelinepathlab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication
public class LifelinepathlabApplication {

	public static void main(String[] args) {
		SpringApplication.run(LifelinepathlabApplication.class, args);
		System.out.println("Hello From Lifeline Pathology Lab");
	}

}

