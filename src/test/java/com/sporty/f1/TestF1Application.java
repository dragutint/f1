package com.sporty.f1;

import org.springframework.boot.SpringApplication;

public class TestF1Application {

	public static void main(String[] args) {
		SpringApplication.from(F1Application::main).with(TestcontainersConfiguration.class).run(args);
	}

}
