package com.catchtabling.restaurant.domain;

import com.catchtabling.common.domain.BaseTimeEntity;
import com.catchtabling.common.domain.Code;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name ="RESTAURANT")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Restaurant extends BaseTimeEntity {
    private static final int MAX_NAME_LENGTH = 50;
    private static final int MAX_TEL_NO_LENGTH = 12;
    private static final int MAX_INTRO_LENGTH = 255;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    @NotNull(message = "가게 코드는 Null 일 수 없습니다.")
    private Code code;

    @Size(max = MAX_NAME_LENGTH)
    @NotNull(message = "이름은 Null 일 수 없습니다.")
    private String name;

    @Size(max = MAX_TEL_NO_LENGTH)
    @NotNull(message = "전화번호는 Null 일 수 없습니다.")
    @Column(name = "tel_no")
    private String telNumber;

    @Size(max = MAX_INTRO_LENGTH)
    @NotNull(message = "소개는 Null 일 수 없습니다.")
    private String intro;

    @NotNull(message = "주소는 Null 일 수 없습니다.")
    private String address;

    @NotNull(message = "상태는 Null 일 수 없습니다.")
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(10)")
    private OpenState state;

    @NotNull(message = "수용 가능 인원은 Null 일 수 없습니다.")
    private Integer capacity;

    @Embedded
    private RestaurantDuration restaurantDuration;

    @Builder
    public Restaurant(Code code,
                      String name,
                      String telNumber,
                      String intro,
                      String address,
                      OpenState state,
                      Integer capacity,
                      RestaurantDuration restaurantDuration) {
        this.code = code;
        this.name = name;
        this.telNumber = telNumber;
        this.intro = intro;
        this.address = address;
        this.state = state;
        this.capacity = capacity;
        this.restaurantDuration = restaurantDuration;
    }


    public String getCode() {
        return code.getCode();
    }
}
