package com.banking.application.training.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class BankingEurekaApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankingEurekaApplication.class, args);
	}

}
