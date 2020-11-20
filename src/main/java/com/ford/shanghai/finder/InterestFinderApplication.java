package com.ford.shanghai.finder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class InterestFinderApplication {

	public static void main(String[] args) {
		SpringApplication.run(InterestFinderApplication.class, args);
	}

}
