package com.fiap.tech.restaurante.domain.useCase.restaurant;

import com.fiap.tech.restaurante.domain.mappers.RestaurantMapper;
import com.fiap.tech.restaurante.domain.model.Restaurant;
import com.fiap.tech.restaurante.infra.entity.RestaurantEntity;
import com.fiap.tech.restaurante.infra.repository.RestaurantRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FindRestaurantUseCase {

	private RestaurantRepository restaurantRepository;

	private RestaurantMapper mapper;

	public List<Restaurant> execute(String name, String neighborhood, String city, String state, String cuisineType) {
		Specification<RestaurantEntity> spec = Specification.where(null);

		if (name != null) {
			spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), "%" + name + "%"));
		}
		if (neighborhood != null) {
			spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("neighborhood"),
					"%" + neighborhood + "%"));
		}
		if (city != null) {
			spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("city"), "%" + city + "%"));
		}
		if (state != null) {
			spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("state"), state));
		}
		if (cuisineType != null) {
			spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("cuisineType"),
					"%" + cuisineType + "%"));
		}

		return restaurantRepository.findAll(spec).stream().map(mapper::toDomain).toList();
	}

}
