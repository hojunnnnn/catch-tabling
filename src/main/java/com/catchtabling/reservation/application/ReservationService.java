package com.catchtabling.reservation.application;

import com.catchtabling.common.domain.Code;
import com.catchtabling.common.domain.CodeGenerator;
import com.catchtabling.common.domain.RandomNumericGenerator;
import com.catchtabling.common.exception.customex.*;
import com.catchtabling.member.application.MemberReader;
import com.catchtabling.member.domain.Member;
import com.catchtabling.reservation.domain.Reservation;
import com.catchtabling.reservation.domain.EntryState;
import com.catchtabling.reservation.dto.MemberReservationResponse;
import com.catchtabling.reservation.dto.MemberReservationsResponse;
import com.catchtabling.reservation.dto.ReservationV1Request;
import com.catchtabling.reservation.dto.ReservationV1Response;
import com.catchtabling.reservation.repository.ReservationRepository;
import com.catchtabling.restaurant.application.RestaurantReader;
import com.catchtabling.restaurant.domain.Restaurant;
import com.catchtabling.restaurant.domain.RestaurantDuration;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ReservationService {
    private final static CodeGenerator codeGenerator = new RandomNumericGenerator();
    private final static int MIN_VISITOR_COUNT = 1;

    private final RestaurantReader restaurantReader;
    private final MemberReader memberReader;
    private final ReservationScheduler reservationScheduler;
    private final ReservationRepository reservationRepository;


    @Transactional(readOnly = true)
    public MemberReservationResponse getDetails(String reservationNum) {
        Reservation reservation = reservationRepository.findByReservationNumWithFetch(new Code(reservationNum))
                .orElseThrow(() -> new NotFoundException(ErrorCode.RESERVATION_NOT_FOUND));

        return MemberReservationResponse.from(reservation);
    }

    @Transactional(readOnly = true)
    public MemberReservationsResponse getReservationList(Long memberId,
                                                         EntryState state,
                                                         Pageable pageable) {
        List<Reservation> reservations = reservationRepository.findAllByMemberIdAndState(
                memberId,
                state,
                pageable);

        return MemberReservationsResponse.from(reservations);
    }

    @Transactional
    public ReservationV1Response reserve(ReservationV1Request request) {
        Member member = memberReader.getMember(request.memberId());
        Restaurant restaurant = restaurantReader.getRestaurant(request.restaurantId());
        
        validate(restaurant, member, request);

        reservationScheduler.generate(
                restaurant,
                request.visitDateTime(),
                request.visitorCount()
        );

        Reservation reservation = reservationRepository.save(
                Reservation.builder()
                .reservationNumber(createReservationNum())
                .requestMemo(request.requestMemo())
                .visitorCount(request.visitorCount())
                .visitDateTime(request.visitDateTime())
                .member(member)
                .restaurant(restaurant)
                .build()
        );

        return ReservationV1Response.from(reservation);
    }

    private void validate(Restaurant restaurant, Member member, ReservationV1Request request) {
        validateAlreadyReserved(
                restaurant,
                member,
                request.visitDateTime());
        validateVisitTime(
                restaurant.getRestaurantDuration(),
                request.visitDateTime()
        );
        validateVisitor(
                restaurant.getCapacity(),
                request.visitorCount());
    }
    private void validateAlreadyReserved(Restaurant restaurant,
                                         Member member,
                                         LocalDateTime visitDateTime) {
        if (reservationRepository.existsByRestaurantAndMemberAndVisitDateTime(restaurant, member, visitDateTime)) {
            throw new AlreadyReservedException(ErrorCode.ALREADY_RESERVED);
        }
    }
    private void validateVisitTime(RestaurantDuration duration, LocalDateTime visitDateTime) {
        if (duration.isNotInDuration(visitDateTime.toLocalTime())) {
            throw new BadRequestException(ErrorCode.INVALID_RESERVE_STORE_DURATION);
        }
    }
    private void validateVisitor(Integer capacity, Integer visitorCount) {
        if (visitorCount < MIN_VISITOR_COUNT) {
            throw new BadRequestException(ErrorCode.INVALID_VISITOR_MIN_SIZE);
        } else if(visitorCount > capacity) {
            throw new BadRequestException(ErrorCode.EXCEED_VISITOR_SIZE);
        }
    }

    private Code createReservationNum() {
        Code reservationNum;
        do {
            reservationNum = Code.generateCode(codeGenerator);
        } while (reservationRepository.existsByReservationNumber(reservationNum));
        return reservationNum;
    }

}
