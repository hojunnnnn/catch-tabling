package com.catchtabling.waiting.domain;

import com.catchtabling.common.domain.BaseTimeEntity;
import com.catchtabling.member.domain.Member;
import com.catchtabling.store.domain.Store;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Table(name = "REMOTE_WAITING")
@Entity
public class Waiting extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "대기 번호는 Null 일 수 없습니다.")
    @Column(name = "wait_number")
    private Integer waitNumber;

    @NotNull(message = "인원 수는 Null 일 수 없습니다.")
    @Column(name = "party_cnt")
    private Integer partyCount;

    // 0: 대기 1: 확정대기, 2: 입장완료, 3: 취소
    @NotNull(message = "상태는 Null 일 수 없습니다.")
    @Column(name = "status")
    private Integer status;

    @Column(name = "notified_date")
    private LocalDateTime notifiedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_info_id")
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_info_id")
    private Member member;

    @Builder
    public Waiting(Integer waitNumber, Integer partyCount, Integer status, LocalDateTime notifiedDate, Store store, Member member) {
        this.waitNumber = waitNumber;
        this.partyCount = partyCount;
        this.status = status;
        this.notifiedDate = notifiedDate;
        this.store = store;
        this.member = member;
    }
}