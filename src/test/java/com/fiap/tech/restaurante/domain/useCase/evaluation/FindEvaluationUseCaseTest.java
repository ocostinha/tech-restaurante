package com.fiap.tech.restaurante.domain.useCase.evaluation;

import com.fiap.tech.restaurante.domain.mappers.EvaluationMapper;
import com.fiap.tech.restaurante.domain.model.Evaluation;
import com.fiap.tech.restaurante.infra.entity.EvaluationEntity;
import com.fiap.tech.restaurante.infra.repository.EvaluationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class FindEvaluationUseCaseTest {

	@InjectMocks
	private FindEvaluationUseCase findEvaluationUseCase;

	@Mock
	private EvaluationRepository evaluationRepository;

	@Mock
	private EvaluationMapper evaluationMapper;

	private Evaluation evaluation;

	private EvaluationEntity evaluationEntity;

	@BeforeEach
	void setUp() {
		openMocks(this);
		evaluation = new Evaluation(1L, 1L, 1L, "Great service", 5, null, null);
		evaluationEntity = new EvaluationEntity(1L, 1L, 1L, "Great service", 5, null, null);
	}

	@Test
	void shouldReturnEvaluationsByRestaurant() {
		when(evaluationRepository.findByIdRestaurant(1L)).thenReturn(List.of(evaluationEntity));
		when(evaluationMapper.toDomain(evaluationEntity)).thenReturn(evaluation);

		List<Evaluation> result = findEvaluationUseCase.execute(1L, null);

		assertEquals(1, result.size());
		assertEquals(evaluation.getId(), result.getFirst().getId());
		verify(evaluationRepository).findByIdRestaurant(1L);
		verify(evaluationMapper).toDomain(evaluationEntity);
	}

	@Test
	void shouldReturnEvaluationByReserve() {
		when(evaluationRepository.findByIdReserve(1L)).thenReturn(Optional.of(evaluationEntity));
		when(evaluationMapper.toDomain(evaluationEntity)).thenReturn(evaluation);

		List<Evaluation> result = findEvaluationUseCase.execute(null, 1L);

		assertEquals(1, result.size());
		assertEquals(evaluation.getId(), result.getFirst().getId());
		verify(evaluationRepository).findByIdReserve(1L);
		verify(evaluationMapper).toDomain(evaluationEntity);
	}

	@Test
	void shouldReturnEmptyListWhenNoEvaluationsByRestaurant() {
		when(evaluationRepository.findByIdRestaurant(1L)).thenReturn(List.of());

		List<Evaluation> result = findEvaluationUseCase.execute(1L, null);

		assertTrue(result.isEmpty());

		verify(evaluationRepository).findByIdRestaurant(1L);
	}

}
