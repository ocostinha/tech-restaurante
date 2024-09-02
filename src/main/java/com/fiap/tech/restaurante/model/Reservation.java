package com.fiap.tech.restaurante.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime creationDateTime;
    private LocalDateTime updateDateTime;
    private String status;

    @PrePersist
    protected void onCreate() {
        this.creationDateTime = LocalDateTime.now();
        this.updateDateTime = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updateDateTime = LocalDateTime.now();
    }
}

