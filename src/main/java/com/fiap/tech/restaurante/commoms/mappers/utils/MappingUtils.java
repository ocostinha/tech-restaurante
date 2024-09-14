package com.fiap.tech.restaurante.commoms.mappers.utils;

import java.time.LocalDateTime;
import java.util.UUID;

public class MappingUtils {
    public static final String LOCAL_DATE_TIME_NOW =
            "java(com.fiap.postech.estacionamento.commoms.mappers.utils.MappingUtils.localDateTimeNow())";
    public static final String RANDOM_UUID =
            "java(com.fiap.postech.estacionamento.commoms.mappers.utils.MappingUtils.randomUUID())";

    public static LocalDateTime localDateTimeNow() {
        return LocalDateTime.now();
    }
    public static UUID randomUUID() {
        return UUID.randomUUID();
    }
}