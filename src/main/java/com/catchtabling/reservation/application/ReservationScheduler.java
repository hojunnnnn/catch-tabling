package com.catchtabling.reservation.application;

import com.catchtabling.reservation.domain.ReservationSchedule;
import com.catchtabling.reservation.repository.ReservationScheduleRepository;
import com.catchtabling.restaurant.domain.Restaurant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReservationScheduler {

    private final ReservationScheduleRepository scheduleRepository;

    @Transactional
    public ReservationSchedule generate(Restaurant restaurant,
                                        LocalDateTime visitDateTime,
                                        Integer visitorCount) {

        ReservationSchedule schedule = scheduleRepository.findByRestaurantIdAndReservationDateTime(restaurant.getId(), visitDateTime);

        if(schedule == null) {
            schedule = scheduleRepository.save(
                    new ReservationSchedule(
                            restaurant.getId(),
                            visitDateTime,
                            restaurant.getCapacity()
                    )
            );
        }

        schedule.reserve(visitorCount);
        return schedule;
    }
}
