package com.fiap.tech.restaurante.application.controller;

import com.fiap.tech.restaurante.application.dto.EvaluationRequestDTO;
import com.fiap.tech.restaurante.application.dto.EvaluationResponseDTO;
import com.fiap.tech.restaurante.domain.mappers.EvaluationMapper;
import com.fiap.tech.restaurante.domain.model.Evaluation;
import com.fiap.tech.restaurante.domain.useCase.evaluation.CreateEvaluationUseCase;
import com.fiap.tech.restaurante.domain.useCase.evaluation.FindEvaluationUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class EvaluationControllerTest {

    @InjectMocks
    private EvaluationController evaluationController;

    @Mock
    private CreateEvaluationUseCase createEvaluationUseCase;

    @Mock
    private FindEvaluationUseCase findEvaluationUseCase;

    @Mock
    private EvaluationMapper evaluationMapper;

    private EvaluationRequestDTO evaluationRequestDTO;
    private Evaluation evaluation;
    private EvaluationResponseDTO evaluationResponseDTO;

    @BeforeEach
    void setUp() {
        openMocks(this);
        evaluationRequestDTO = new EvaluationRequestDTO(1L, 1L, "Ótimo restaurante", 5);
        evaluation = new Evaluation(1L, 1L, 1L, "Ótimo restaurante", 5, LocalDateTime.now(), LocalDateTime.now());
        evaluationResponseDTO = new EvaluationResponseDTO(1L, 1L, 1L, "Ótimo restaurante", 5, LocalDateTime.now(), LocalDateTime.now());
    }

    @Test
    void createEvaluationSuccessfully() {
        when(evaluationMapper.toDomain(any(EvaluationRequestDTO.class))).thenReturn(evaluation);
        when(createEvaluationUseCase.execute(any(Evaluation.class))).thenReturn(evaluation);
        when(evaluationMapper.toResponseDTO(any(Evaluation.class))).thenReturn(evaluationResponseDTO);

        EvaluationResponseDTO result = evaluationController.createEvaluation(evaluationRequestDTO);

        assertNotNull(result);
        assertEquals(evaluationResponseDTO.getId(), result.getId());
        assertEquals(evaluationResponseDTO.getIdRestaurant(), result.getIdRestaurant());
        assertEquals(evaluationResponseDTO.getIdReserve(), result.getIdReserve());

        verify(createEvaluationUseCase).execute(any(Evaluation.class));
        verify(evaluationMapper).toResponseDTO(any(Evaluation.class));
    }

    @Test
    void findEvaluationsSuccessfully() {
        List<Evaluation> evaluations = new ArrayList<>();
        evaluations.add(evaluation);
        when(findEvaluationUseCase.execute(1L, null)).thenReturn(evaluations);
        when(evaluationMapper.toResponseDTO(any(Evaluation.class))).thenReturn(evaluationResponseDTO);

        List<EvaluationResponseDTO> result = evaluationController.findReservations(1L, null);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(evaluationResponseDTO.getId(), result.get(0).getId());
        assertEquals(evaluationResponseDTO.getIdRestaurant(), result.get(0).getIdRestaurant());
        assertEquals(evaluationResponseDTO.getIdReserve(), result.get(0).getIdReserve());

        verify(findEvaluationUseCase).execute(1L, null);
        verify(evaluationMapper).toResponseDTO(any(Evaluation.class));
    }

    @Test
    void findEvaluationsByReserveIdSuccessfully() {
        List<Evaluation> evaluations = new ArrayList<>();
        evaluations.add(evaluation);
        when(findEvaluationUseCase.execute(null, 1L)).thenReturn(evaluations);
        when(evaluationMapper.toResponseDTO(any(Evaluation.class))).thenReturn(evaluationResponseDTO);

        List<EvaluationResponseDTO> result = evaluationController.findReservations(null, 1L);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(evaluationResponseDTO.getId(), result.get(0).getId());
        assertEquals(evaluationResponseDTO.getIdRestaurant(), result.get(0).getIdRestaurant());
        assertEquals(evaluationResponseDTO.getIdReserve(), result.get(0).getIdReserve());

        verify(findEvaluationUseCase).execute(null, 1L);
        verify(evaluationMapper).toResponseDTO(any(Evaluation.class));
    }

    @Test
    void shouldThrowResponseStatusExceptionWhenEvaluationNotFound() {
        when(findEvaluationUseCase.execute(2L, null)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> evaluationController.findReservations(2L, null));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        verify(findEvaluationUseCase).execute(2L, null);
    }
}
