package com.fiap.tech.restaurante;

import io.cucumber.junit.Cucumber;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = RestauranteApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@RunWith(Cucumber.class)
@ActiveProfiles("test")
class RestauranteApplicationTest {

	@Test
	void contextLoads() {
	}

}