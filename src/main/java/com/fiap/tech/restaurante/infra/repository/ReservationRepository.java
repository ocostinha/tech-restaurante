package com.fiap.tech.restaurante.infra.repository;

import com.fiap.tech.restaurante.infra.entity.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository
		extends JpaRepository<ReservationEntity, Long>, JpaSpecificationExecutor<ReservationEntity> {

}
