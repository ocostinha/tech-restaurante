package bdd.stepDefinitions;

import com.fiap.tech.restaurante.RestauranteApplication;
import com.fiap.tech.restaurante.application.dto.RestaurantResponseDTO;
import com.fiap.tech.restaurante.domain.exception.ErrorDetails;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import mocks.RestaurantRequestDTOMock;
import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@SpringBootTest(classes = RestauranteApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
@CucumberContextConfiguration
public class CreateRestaurantStepDefinitionsTest {

	private final RestTemplate restTemplate = new RestTemplate();

	private RestaurantResponseDTO response;

	private ErrorDetails errorDetails;

	private Integer responseStatusCode;

	@Then("The client receives status code of {int}")
	public void theClientReceivesStatusCodeOf(int statusCode) {
		Assert.assertEquals(statusCode, (int) responseStatusCode);
	}

	@NotNull
	private ResponseEntity<RestaurantResponseDTO> createMockedRestaurant(String restaurantName) {
		return restTemplate.postForEntity("http://localhost:8080/restaurant/create",
				RestaurantRequestDTOMock.mock(restaurantName), RestaurantResponseDTO.class);
	}

	@Given("Exists a restaurant with name {string}")
	public void existsARestaurantWithName(String restaurantName) {
		try {
			createMockedRestaurant(restaurantName);
		}
		catch (Exception ex) {
			System.out.println("Restaurante já cadastrado");
		}
	}

	@And("The client receives response body with message {string}")
	public void theClientReceivesResponseBodyWithMessage(String errorMessage) {
		Assert.assertEquals(errorMessage, errorDetails.getMessage());
	}

	@When("The client calls endpoint to create with valid payload for restaurant {string}")
	public void theClientCallsEndpointToCreateWithValidPayloadForRestaurant(String restaurantName) {
		try {
			var restResponse = createMockedRestaurant(restaurantName);

			response = restResponse.getBody();
			responseStatusCode = restResponse.getStatusCode().value();
		}
		catch (HttpStatusCodeException ex) {
			errorDetails = ex.getResponseBodyAs(ErrorDetails.class);
			responseStatusCode = ex.getStatusCode().value();
		}
	}

	@And("The client receives response body with restaurant name {string}")
	public void theClientReceivesResponseBodyWithRestaurantName(String restaurantName) {
		Assert.assertEquals(restaurantName, Objects.requireNonNull(response).getName());
	}

}
