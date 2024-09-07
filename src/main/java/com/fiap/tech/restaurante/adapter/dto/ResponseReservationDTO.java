package com.fiap.tech.restaurante.adapter.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResponseReservationDTO {
    private UUID id;
    private LocalDateTime creationDate;
    private LocalDateTime updateDate;
}