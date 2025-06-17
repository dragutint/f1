package com.sporty.f1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class F1Application {

	public static void main(String[] args) {
		SpringApplication.run(F1Application.class, args);
	}

}
