package com.fiap.tech.restaurante.infra.repository;

import com.fiap.tech.restaurante.infra.entity.EvaluationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EvaluationRepository extends JpaRepository<EvaluationEntity, Long> {

	List<EvaluationEntity> findByIdRestaurant(Long idRestaurant);

	Optional<EvaluationEntity> findByIdReserve(Long idReserve);

}
