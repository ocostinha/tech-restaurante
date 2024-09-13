package com.fiap.tech.restaurante.domain.repository;

import com.fiap.tech.restaurante.adapter.mapper.ReservationMapper;
import com.fiap.tech.restaurante.domain.entity.Reservation;
import com.fiap.tech.restaurante.infrastructure.repository.JpaReservationRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@AllArgsConstructor
@Component
public class ReservationRepositoryImpl implements  ReservationRepository{

    @Autowired
private  final JpaReservationRepository repository;
    private  final  ReservationMapper mapper;

    @Override
    public  Reservation     findReservationById(UUID id){
        return repository.findById(id).map(entity -> mapper.toDomain(entity)).orElse(null);
    }
}
