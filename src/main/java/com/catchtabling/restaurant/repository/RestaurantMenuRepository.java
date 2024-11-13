package com.catchtabling.restaurant.repository;

import com.catchtabling.restaurant.domain.RestaurantMenu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantMenuRepository extends JpaRepository<RestaurantMenu, Long> {
}
