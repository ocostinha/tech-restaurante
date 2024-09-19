Feature: Create restaurant
  Scenario: Client create a new restaurant with success
    When The client calls endpoint to create with valid payload for restaurant "Teste 123"
    Then The client receives status code of 201
    And The client receives response body with restaurant name "Teste 123"
  Scenario: Client try create a new restaurant but restaurant's name already exists
    Given Exists a restaurant with name "Restaurante Teste"
    When The client calls endpoint to create with valid payload for restaurant "Teste 123"
    Then The client receives status code of 422
    And The client receives response body with message "Já existe um restaurante com esse nome na plataforma."