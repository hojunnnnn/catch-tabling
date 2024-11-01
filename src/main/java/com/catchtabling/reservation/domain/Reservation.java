package com.catchtabling.reservation.domain;

import com.catchtabling.common.domain.BaseTimeEntity;
import com.catchtabling.common.domain.Code;
import com.catchtabling.member.domain.Member;
import com.catchtabling.store.domain.Store;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        name = "RESERVATION",
        uniqueConstraints = @UniqueConstraint(columnNames = {"store_info_id", "visit_date"})
)

@Entity
public class Reservation extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "예약 번호는 Null 일 수 없습니다.")
    @AttributeOverride(name = "code", column = @Column(name = "reservation_no"))
    @Embedded
    private Code reservationNumber;

    @NotNull(message = "인원 수는 Null 일 수 없습니다.")
    @Column(name = "visitor_cnt")
    private Integer visitorCount;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "상태는 Null 일 수 없습니다.")
    @Column(name = "state", columnDefinition = "varchar(10)")
    private EntryState state;

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
    public Reservation(Code reservationNumber, Integer visitorCount, String requestMemo, LocalDateTime visitDateTime, Store store, Member member) {
        this.reservationNumber = reservationNumber;
        this.visitorCount = visitorCount;
        this.state = EntryState.PENDING;
        this.requestMemo = requestMemo;
        this.visitDateTime = visitDateTime;
        this.store = store;
        this.member = member;
    }

    public String getReservationNumber() {
        return reservationNumber.getCode();
    }
}
