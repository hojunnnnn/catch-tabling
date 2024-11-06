package com.catchtabling.reservation.application;

import com.catchtabling.reservation.domain.ReservationSchedule;
import com.catchtabling.reservation.repository.ReservationScheduleRepository;
import com.catchtabling.store.domain.Store;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReservationScheduler {

    private final ReservationScheduleRepository scheduleRepository;

    @Transactional
    public ReservationSchedule generate(Store store,
                                        LocalDateTime visitDateTime,
                                        Integer visitorCount) {

        ReservationSchedule schedule = scheduleRepository.findByStoreIdAndReservationDateTime(store.getId(), visitDateTime);

        if(schedule == null) {
            schedule = scheduleRepository.save(
                    new ReservationSchedule(
                            store.getId(),
                            visitDateTime,
                            store.getCapacity()
                    )
            );
        }

        schedule.reserve(visitorCount);
        return schedule;
    }
}
