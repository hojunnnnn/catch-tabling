package com.catchtabling.reservation.application;

import com.catchtabling.common.domain.Code;
import com.catchtabling.common.domain.CodeGenerator;
import com.catchtabling.common.domain.RandomNumericGenerator;
import com.catchtabling.common.exception.customex.AlreadyReservedException;
import com.catchtabling.common.exception.customex.BadRequestException;
import com.catchtabling.common.exception.customex.ErrorCode;
import com.catchtabling.member.application.MemberReader;
import com.catchtabling.member.domain.Member;
import com.catchtabling.reservation.domain.Reservation;
import com.catchtabling.reservation.dto.ReservationV1Request;
import com.catchtabling.reservation.dto.ReservationV1Response;
import com.catchtabling.reservation.repository.ReservationRepository;
import com.catchtabling.store.application.StoreReader;
import com.catchtabling.store.domain.Store;
import com.catchtabling.store.domain.StoreDuration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class ReservationService {
    private final static CodeGenerator codeGenerator = new RandomNumericGenerator();
    private final static int MIN_VISITOR_COUNT = 1;

    private final StoreReader storeReader;
    private final MemberReader memberReader;
    private final ReservationRepository reservationRepository;

    @Transactional
    public ReservationV1Response reserve(ReservationV1Request request) {
        Member member = memberReader.findMemberById(request.memberId());
        Store store = storeReader.findStoreById(request.storeId());

        validate(store, request);

        Reservation reservation = reservationRepository.save(Reservation.builder()
                .reservationNumber(createReservationNum())
                .requestMemo(request.requestMemo())
                .visitorCount(request.visitorCount())
                .visitDateTime(request.visitDateTime())
                .member(member)
                .store(store)
                .build());

        return ReservationV1Response.from(reservation);
    }

    public void validate(Store store, ReservationV1Request request) {
        validateVisitTime(
                store.getStoreDuration(),
                request.visitDateTime());
        validateAlreadyReserved(
                store,
                request.visitDateTime());
        validateVisitor(request.visitorCount());
    }
    private void validateVisitTime(StoreDuration duration, LocalDateTime visitDateTime) {
        if (duration.isNotInDuration(visitDateTime.toLocalTime())) {
            throw new BadRequestException(ErrorCode.INVALID_RESERVE_STORE_DURATION);
        }
    }
    private void validateAlreadyReserved(Store store, LocalDateTime visitDateTime) {
        if (reservationRepository.existsByStoreAndVisitDateTime(store, visitDateTime)) {
            throw new AlreadyReservedException(ErrorCode.ALREADY_RESERVED);
        }
    }
    private void validateVisitor(Integer visitorCount) {
        if (visitorCount < MIN_VISITOR_COUNT) {
            throw new BadRequestException(ErrorCode.INVALID_VISITOR_MIN_SIZE);
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
