package com.catchtabling.restaurant.application;

import com.catchtabling.common.exception.customex.ErrorCode;
import com.catchtabling.common.exception.customex.NotFoundException;
import com.catchtabling.restaurant.domain.Restaurant;
import com.catchtabling.restaurant.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RestaurantReader {
    private final RestaurantRepository restaurantRepository;

    @Transactional(readOnly = true)
    public Restaurant getRestaurant(Long restaurantId) {
        return restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.RESTAURANT_NOT_FOUND));
    }
}
