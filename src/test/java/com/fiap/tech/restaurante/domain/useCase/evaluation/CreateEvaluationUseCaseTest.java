package com.fiap.tech.restaurante.domain.useCase.evaluation;

import com.fiap.tech.restaurante.domain.enums.ReservationStatus;
import com.fiap.tech.restaurante.domain.exception.BusinessException;
import com.fiap.tech.restaurante.domain.mappers.EvaluationMapper;
import com.fiap.tech.restaurante.domain.model.Evaluation;
import com.fiap.tech.restaurante.domain.model.Reservation;
import com.fiap.tech.restaurante.domain.useCase.reservation.FindReservationByIdUseCase;
import com.fiap.tech.restaurante.infra.entity.EvaluationEntity;
import com.fiap.tech.restaurante.infra.repository.EvaluationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class CreateEvaluationUseCaseTest {

	@InjectMocks
	private CreateEvaluationUseCase createEvaluationUseCase;

	@Mock
	private EvaluationRepository evaluationRepository;

	@Mock
	private FindReservationByIdUseCase findReservationByIdUseCase;

	@Mock
	private EvaluationMapper evaluationMapper;

	private Evaluation evaluation;

	private Reservation reservation;

	private EvaluationEntity evaluationEntity;

	@BeforeEach
	void setUp() {
		openMocks(this);

		evaluation = new Evaluation(1L, 1L, 1L, "Ótimo restaurante", 5, null, null);
		reservation = new Reservation(1L, 1L, "John Doe", "john@example.com", null, null, 4,
				ReservationStatus.COMPLETED, null, null);
		evaluationEntity = new EvaluationEntity(1L, 1L, 1L, "Ótimo restaurante", 5, null, null);
	}

	@Test
	void shouldCreateEvaluationSuccessfully() {
		when(findReservationByIdUseCase.execute(evaluation.getIdRestaurant())).thenReturn(reservation);
		when(evaluationRepository.findByIdReserve(evaluation.getIdReserve())).thenReturn(Optional.empty());
		when(evaluationMapper.toEntity(any(Evaluation.class))).thenReturn(evaluationEntity);
		when(evaluationRepository.save(evaluationEntity)).thenReturn(evaluationEntity);
		when(evaluationMapper.toDomain(evaluationEntity)).thenReturn(evaluation);

		Evaluation result = createEvaluationUseCase.execute(evaluation);

		assertEquals(evaluation.getId(), result.getId());

		verify(findReservationByIdUseCase).execute(evaluation.getIdRestaurant());
		verify(evaluationRepository).findByIdReserve(evaluation.getIdReserve());
		verify(evaluationRepository).save(evaluationEntity);
	}

	@Test
	void shouldThrowBusinessExceptionWhenReservationNotCompleted() {
		reservation.setStatus(ReservationStatus.CONFIRMED);
		when(findReservationByIdUseCase.execute(evaluation.getIdRestaurant())).thenReturn(reservation);

		BusinessException exception = assertThrows(BusinessException.class,
				() -> createEvaluationUseCase.execute(evaluation));

		assertEquals("Apenas reservas que foram completas podem ser avaliadas", exception.getMessage());

		verify(findReservationByIdUseCase).execute(evaluation.getIdRestaurant());
		verify(evaluationRepository, never()).findByIdReserve(any());
		verify(evaluationRepository, never()).save(any());
	}

	@Test
	void shouldThrowBusinessExceptionWhenEvaluationAlreadyExistsForReserve() {
		when(findReservationByIdUseCase.execute(evaluation.getIdRestaurant())).thenReturn(reservation);
		when(evaluationRepository.findByIdReserve(evaluation.getIdReserve())).thenReturn(Optional.of(evaluationEntity));

		BusinessException exception = assertThrows(BusinessException.class,
				() -> createEvaluationUseCase.execute(evaluation));

		assertEquals("Apenas uma avaliação pode ser realizada para esta reserva", exception.getMessage());

		verify(findReservationByIdUseCase).execute(evaluation.getIdRestaurant());
		verify(evaluationRepository).findByIdReserve(evaluation.getIdReserve());
		verify(evaluationRepository, never()).save(any());
	}

}
