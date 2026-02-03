package com.careers.CareerHub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class CareerHubApplication {

	public static void main(String[] args) {
		SpringApplication.run(CareerHubApplication.class, args);
	}

}
