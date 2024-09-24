package com.fiap.tech.restaurante.infra.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "availables")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AvailableEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "id_restaurant", nullable = false)
	private Long idRestaurant;

	@Column(name = "available_date", nullable = false)
	private LocalDate date;

	@Column(name = "available_hour", nullable = false)
	private LocalTime hour;

	@Column(name = "available_seats", nullable = false)
	private int availableSeats;

	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	@PrePersist
	protected void onCreate() {
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
	}

	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = LocalDateTime.now();
	}

}
