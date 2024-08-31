package com.fiap.tech.restaurante;

import org.springframework.boot.SpringApplication;

public class TestRestauranteApplication {

	public static void main(String[] args) {
		SpringApplication.from(RestauranteApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
