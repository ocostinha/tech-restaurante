package com.fiap.tech.restaurante.domain.mappers.utils;

import com.fiap.tech.restaurante.domain.enums.ReservationStatus;

public class MappingUtils {
    public static final String PENDING_STATUS =
            "java(com.fiap.postech.estacionamento.commoms.mappers.utils.MappingUtils.reservationStatusPending())";

    public static ReservationStatus reservationStatusPending() {
        return ReservationStatus.PENDING;
    }
}
