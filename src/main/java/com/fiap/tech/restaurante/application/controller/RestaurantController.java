package com.fiap.tech.restaurante.application.controller;

import com.fiap.tech.restaurante.application.dto.RestaurantRequestDTO;
import com.fiap.tech.restaurante.application.dto.RestaurantResponseDTO;
import com.fiap.tech.restaurante.domain.exception.ErrorDetails;
import com.fiap.tech.restaurante.domain.mappers.RestaurantMapper;
import com.fiap.tech.restaurante.domain.useCase.restaurant.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurant")
@RequiredArgsConstructor
public class RestaurantController {

    private final CreateRestaurantUseCase createRestaurantUseCase;
    private final UpdateRestaurantUseCase updateRestaurantUseCase;
    private final FindRestaurantUseCase findRestaurantUseCase;
    private final FindRestaurantByIdUseCase findRestaurantByIdUseCase;
    private final DeleteRestaurantByIdUseCase deleteRestaurantByIdUseCase;
    private final RestaurantMapper mapper;

    @Operation(summary = "Cadastrar restaurante")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Restaurante cadastrado com sucesso",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RestaurantResponseDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Erro na validação de campos",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetails.class)))
    })
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public RestaurantResponseDTO createRestaurant(@Valid @RequestBody RestaurantRequestDTO requestDTO) {
        return mapper.toResponse(
                createRestaurantUseCase.execute(mapper.toDomain(requestDTO))
        );
    }

    @Operation(summary = "Atualizar restaurante")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Restaurante atualizado com sucesso",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RestaurantResponseDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Erro na validação de campos",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(responseCode = "422", description = "Erro na regra de negócio",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetails.class)))
    })
    @PutMapping("/edit/{id}")
    @ResponseStatus(HttpStatus.OK)
    public RestaurantResponseDTO editRestaurant(
            @PathVariable Long id,
            @Valid @RequestBody RestaurantRequestDTO requestDTO
    ) {
        return mapper.toResponse(
                updateRestaurantUseCase.execute(id, mapper.toDomain(requestDTO))
        );
    }

    @Operation(summary = "Consultar restaurantes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Restaurante localizados",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RestaurantResponseDTO.class)) }),
            @ApiResponse(responseCode = "204", description = "Não existem restaurantes cadastrados")
    })
    @GetMapping("/get")
    @ResponseStatus(HttpStatus.OK)
    public List<RestaurantResponseDTO> findRestaurants(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String neighborhood,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String state,
            @RequestParam(required = false) String cuisineType) {

        return findRestaurantUseCase.execute(name, neighborhood, city, state, cuisineType).stream()
                .map(mapper::toResponse).toList();
    }

    @Operation(summary = "Consultar restaurante")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Restaurante solicitado",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RestaurantResponseDTO.class)) }),
            @ApiResponse(responseCode = "204", description = "Não existem restaurantes cadastrados")
    })
    @GetMapping("/get/{id}")
    @ResponseStatus(HttpStatus.OK)
    public RestaurantResponseDTO getRestaurant(@PathVariable Long id) {
        return mapper.toResponse(
                findRestaurantByIdUseCase.execute(id)
        );
    }

    @Operation(summary = "Deletar restaurante")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Restaurante deletado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Erro na regra de negócio")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteRestaurant(@PathVariable Long id) {
        deleteRestaurantByIdUseCase.execute(id);
    }
}
