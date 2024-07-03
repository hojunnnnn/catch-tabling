package com.catchtabling.store.domain;

import com.catchtabling.common.domain.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Table(name = "STORE_MENU_INFO")
@Entity
public class StoreMenu extends BaseTimeEntity {

    private static final int MAX_NAME_LENGTH = 30;
    private static final int MAX_IMAGE_URL_LENGTH = 255;
    private static final int MAX_INTRO_LENGTH = 255;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = MAX_NAME_LENGTH)
    @NotNull(message = "이름은 Null 일 수 없습니다.")
    @Column(name = "name")
    private String name;

    @NotNull(message = "가격은 Null 일 수 없습니다.")
    @Column(name = "price")
    private int price;

    @Size(max = MAX_INTRO_LENGTH)
    @NotNull(message = "소개는 Null 일 수 없습니다.")
    @Column(name = "intro")
    private String intro;

    @Size(max = MAX_IMAGE_URL_LENGTH)
    @NotNull(message = "이미지는 Null 일 수 없습니다.")
    @Column(name = "image_url")
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_info_id")
    private Store store;

    @Builder
    public StoreMenu(String name, int price, String intro, String imageUrl, Store store) {
        this.name = name;
        this.price = price;
        this.intro = intro;
        this.imageUrl = imageUrl;
        this.store = store;
    }
}
