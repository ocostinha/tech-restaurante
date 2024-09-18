package com.fiap.tech.restaurante.application.controller;

import com.fiap.tech.restaurante.application.dto.EvaluationRequestDTO;
import com.fiap.tech.restaurante.application.dto.EvaluationResponseDTO;
import com.fiap.tech.restaurante.application.dto.RestaurantResponseDTO;
import com.fiap.tech.restaurante.domain.exception.ErrorDetails;
import com.fiap.tech.restaurante.domain.mappers.EvaluationMapper;
import com.fiap.tech.restaurante.domain.useCase.evaluation.CreateEvaluationUseCase;
import com.fiap.tech.restaurante.domain.useCase.evaluation.FindEvaluationUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/evaluation")
@AllArgsConstructor
public class EvaluationController {

    private final CreateEvaluationUseCase createEvaluationUseCase;
    private final FindEvaluationUseCase findEvaluationUseCase;
    private final EvaluationMapper mapper;

    @Operation(summary = "Avaliar restaurante")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Avaliação recebida com sucess",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RestaurantResponseDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Erro na validação de campos",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(responseCode = "422", description = "Erro de regra de negócio",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetails.class)))
    })
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public EvaluationResponseDTO createEvaluation(@Valid @RequestBody EvaluationRequestDTO request) {
        return mapper.toResponseDTO(
                createEvaluationUseCase.execute(
                        mapper.toDomain(request)
                )
        );
    }

    @Operation(summary = "Obter avaliações")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Avaliações filtradas",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = EvaluationResponseDTO.class))})
    })
    @GetMapping("/get")
    @ResponseStatus(HttpStatus.OK)
    public List<EvaluationResponseDTO> findReservations(
            @RequestParam(required = false) Long idRestaurant,
            @RequestParam(required = false) UUID idReserve
    ) {
        return findEvaluationUseCase.execute(
                idRestaurant,
                idReserve
        ).stream().map(mapper::toResponseDTO).toList();
    }
}
