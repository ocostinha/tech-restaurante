package com.fiap.tech.restaurante.application.controller;

import com.fiap.tech.restaurante.application.dto.ReservationRequestDTO;
import com.fiap.tech.restaurante.application.dto.ReservationResponseDTO;
import com.fiap.tech.restaurante.domain.enums.ReservationStatus;
import com.fiap.tech.restaurante.domain.exception.ErrorDetails;
import com.fiap.tech.restaurante.domain.mappers.ReservationMapper;
import com.fiap.tech.restaurante.domain.useCase.reservation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/available/reserve_request")
@RequiredArgsConstructor
public class ReservationController {

	private final CancelReservationUseCase cancelReservationUseCase;

	private final CompleteReservationUseCase completeReservationUseCase;

	private final CreateReservationUseCase createReservationUseCase;

	private final FindReservationByIdUseCase findReservationByIdUseCase;

	private final UpdateReservationUseCase updateReservationUseCase;

	private final FindReservationUseCase findReservationUseCase;

	private final ReservationMapper mapper;

	@Operation(summary = "Realizar reserva")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Reserva realizada com sucesso",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = ReservationResponseDTO.class)) }),
			@ApiResponse(responseCode = "400", description = "Erro na validação de campos",
					content = @Content(mediaType = "application/json",
							schema = @Schema(implementation = ErrorDetails.class))),
			@ApiResponse(responseCode = "422", description = "Erro de regra de negócio",
					content = @Content(mediaType = "application/json",
							schema = @Schema(implementation = ErrorDetails.class))) })
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ReservationResponseDTO createReservation(@RequestBody @Valid ReservationRequestDTO reservationRequest) {
		return mapper.toResponseDTO(createReservationUseCase.execute(mapper.toDomain(reservationRequest)));
	}

	@Operation(summary = "Obter reserva")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Reserva solicitada",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = ReservationResponseDTO.class)) }),
			@ApiResponse(responseCode = "404", description = "Reserva não localizada",
					content = @Content(mediaType = "application/json",
							schema = @Schema(implementation = ErrorDetails.class))) })
	@GetMapping("/get/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ReservationResponseDTO findReservationById(@PathVariable Long id) {
		return mapper.toResponseDTO(findReservationByIdUseCase.execute(id));
	}

	@Operation(summary = "Obter reservas")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Reservas filtradas",
			content = { @Content(mediaType = "application/json",
					schema = @Schema(implementation = ReservationResponseDTO.class)) }) })
	@GetMapping("/get")
	@ResponseStatus(HttpStatus.OK)
	public List<ReservationResponseDTO> findReservations(@RequestParam Long idRestaurant,
			@RequestParam(required = false) String date, @RequestParam(required = false) String hour,
			@RequestParam(required = false) String status) {
		return findReservationUseCase.execute(idRestaurant, date == null ? null : LocalDate.parse(date),
				hour == null ? null : LocalTime.parse(hour), status == null ? null : ReservationStatus.valueOf(status))
			.stream()
			.map(mapper::toResponseDTO)
			.toList();
	}

	@Operation(summary = "Atualizar reserva")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Reserva atualizada com sucesso",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = ReservationResponseDTO.class)) }),
			@ApiResponse(responseCode = "400", description = "Erro na validação de campos",
					content = @Content(mediaType = "application/json",
							schema = @Schema(implementation = ErrorDetails.class))),
			@ApiResponse(responseCode = "422", description = "Erro de regra de negócio",
					content = @Content(mediaType = "application/json",
							schema = @Schema(implementation = ErrorDetails.class))) })
	@PutMapping("/edit/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ReservationResponseDTO updateReservation(@PathVariable Long id, @RequestBody ReservationRequestDTO request) {
		return mapper.toResponseDTO(updateReservationUseCase.execute(id, mapper.toDomain(request)));
	}

	@Operation(summary = "Concluir reserva")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Reserva concluida com sucesso",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = ReservationResponseDTO.class)) }),
			@ApiResponse(responseCode = "422", description = "Erro de regra de negócio",
					content = @Content(mediaType = "application/json",
							schema = @Schema(implementation = ErrorDetails.class))) })
	@PutMapping("/complete/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ReservationResponseDTO completeReservation(@PathVariable Long id) {
		return mapper.toResponseDTO(completeReservationUseCase.execute(id));
	}

	@Operation(summary = "Cancelar reserva")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Reserva cancelada com sucesso",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = ReservationResponseDTO.class)) }),
			@ApiResponse(responseCode = "422", description = "Erro de regra de negócio",
					content = @Content(mediaType = "application/json",
							schema = @Schema(implementation = ErrorDetails.class))) })
	@DeleteMapping("/cancel/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ReservationResponseDTO cancelReservation(@PathVariable Long id) {
		return mapper.toResponseDTO(cancelReservationUseCase.execute(id));
	}

}
