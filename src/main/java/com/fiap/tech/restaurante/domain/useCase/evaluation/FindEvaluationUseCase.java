package com.fiap.tech.restaurante.domain.useCase.evaluation;

import com.fiap.tech.restaurante.domain.mappers.EvaluationMapper;
import com.fiap.tech.restaurante.domain.model.Evaluation;
import com.fiap.tech.restaurante.infra.repository.EvaluationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FindEvaluationUseCase {

	private final EvaluationRepository repository;

	private final EvaluationMapper mapper;

	public List<Evaluation> execute(Long idRestaurant, Long idReserve) {
		if (idReserve == null) {
			return repository.findByIdRestaurant(idRestaurant).stream().map(mapper::toDomain).toList();
		}

		return List.of(mapper.toDomain(repository.findByIdReserve(idReserve).orElse(null)));
	}

}
