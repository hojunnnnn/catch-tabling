package com.catchtabling.reservation.domain;

import com.catchtabling.common.domain.BaseTimeEntity;
import com.catchtabling.member.domain.Member;
import com.catchtabling.store.domain.Store;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Table(name = "RESERVATION")
@Entity
public class Reservation extends BaseTimeEntity {

    private static final int MAX_RESERVE_NO_LENGTH = 10;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = MAX_RESERVE_NO_LENGTH)
    @NotNull(message = "예약 번호는 Null 일 수 없습니다.")
    @Column(name = "reservation_no")
    private String reservationNumber;

    @NotNull(message = "인원 수는 Null 일 수 없습니다.")
    @Column(name = "visitor_cnt")
    private Integer visitorCount;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "상태는 Null 일 수 없습니다.")
    @Column(columnDefinition = "varchar(10)")
    private ReservationStatus status;

    @Column(name = "request_memo")
    private String requestMemo;

    @NotNull(message = "방문 일시는 Null 일 수 습니다.")
    @Column(name = "visit_date")
    private LocalDateTime visitDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_info_id")
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_info_id")
    private Member member;

    @Builder
    public Reservation(String reservationNumber, Integer visitorCount, String requestMemo, LocalDateTime visitDateTime, Store store, Member member) {
        this.reservationNumber = reservationNumber;
        this.visitorCount = visitorCount;
        this.status = ReservationStatus.PENDING;
        this.requestMemo = requestMemo;
        this.visitDateTime = visitDateTime;
        this.store = store;
        this.member = member;
    }
}
