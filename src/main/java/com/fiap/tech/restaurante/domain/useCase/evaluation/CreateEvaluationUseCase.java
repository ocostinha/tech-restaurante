package com.fiap.tech.restaurante.domain.useCase.evaluation;

import com.fiap.tech.restaurante.domain.enums.ReservationStatus;
import com.fiap.tech.restaurante.domain.exception.BusinessException;
import com.fiap.tech.restaurante.domain.mappers.EvaluationMapper;
import com.fiap.tech.restaurante.domain.model.Evaluation;
import com.fiap.tech.restaurante.domain.model.Reservation;
import com.fiap.tech.restaurante.domain.useCase.reservation.FindReservationByIdUseCase;
import com.fiap.tech.restaurante.infra.repository.EvaluationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CreateEvaluationUseCase {
    private final EvaluationRepository repository;
    private final FindReservationByIdUseCase findReservationByIdUseCase;
    private final EvaluationMapper mapper;

    public Evaluation execute(Evaluation evaluation) {
        Reservation reservation = findReservationByIdUseCase.execute(evaluation.getIdRestaurant());

        if (reservation.getStatus() != ReservationStatus.COMPLETED) {
            throw new BusinessException("Apenas reservas que foram completas podem ser avaliadas");
        }

        return mapper.toDomain(
                repository.save(
                        mapper.toEntity(evaluation)
                )
        );
    }
}
