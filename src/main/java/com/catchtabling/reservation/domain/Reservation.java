package com.catchtabling.reservation.domain;

import com.catchtabling.member.domain.Member;
import com.catchtabling.store.domain.Store;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Table(name = "RESERVATION")
@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "인원 수는 Null 일 수 없습니다.")
    @Column(name = "visitor_cnt")
    private Integer visitorCount;

    @NotNull(message = "상태는 Null 일 수 없습니다.")
    private Integer status;

    @Column(name = "request_memo")
    private String requestMemo;

    @NotNull(message = "예약 일시는 Null 일 수 습니다.")
    @Column(name = "reservation_date")
    private LocalDateTime reservationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_info_id")
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_info_id")
    private Member member;

    @Builder
    public Reservation(Integer visitorCount, Integer status, String requestMemo, LocalDateTime reservationDate, Store store, Member member) {
        this.visitorCount = visitorCount;
        this.status = status;
        this.requestMemo = requestMemo;
        this.reservationDate = reservationDate;
        this.store = store;
        this.member = member;
    }
}
