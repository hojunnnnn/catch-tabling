package com.catchtabling.store.domain;

import com.catchtabling.common.domain.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Table(name ="STORE_INFO")
@Entity
public class Store extends BaseTimeEntity {
    private static final int MAX_NAME_LENGTH = 50;
    private static final int MAX_TEL_NO_LENGTH = 12;
    private static final int MAX_INTRO_LENGTH = 255;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = MAX_NAME_LENGTH)
    @NotNull(message = "이름은 Null 일 수 없습니다.")
    @Column(name = "name")
    private String name;

    @Size(max = MAX_TEL_NO_LENGTH)
    @NotNull(message = "전화번호는 Null 일 수 없습니다.")
    @Column(name = "tell_no")
    private String telNumber;

    @Size(max = MAX_INTRO_LENGTH)
    @NotNull(message = "소개는 Null 일 수 없습니다.")
    @Column(name = "intro")
    private String intro;

    @NotNull(message = "주소는 Null 일 수 없습니다.")
    @Column(name = "address")
    private String address;

    @NotNull(message = "휴무일은 Null 일 수 없습니다.")
    @Column(name = "holiday")
    private String holiday;

    @NotNull(message = "영업 시간은 Null 일 수 없습니다.")
    @Column(name = "open_time")
    private LocalDateTime openTime;

    @NotNull(message = "영업 시간은 Null 일 수 없습니다.")
    @Column(name = "close_time")
    private LocalDateTime closeTime;

    @Builder
    public Store(String name, String telNumber, String intro, String address, String holiday, LocalDateTime openTime, LocalDateTime closeTime) {
        this.name = name;
        this.telNumber = telNumber;
        this.intro = intro;
        this.address = address;
        this.holiday = holiday;
        this.openTime = openTime;
        this.closeTime = closeTime;
    }
}
