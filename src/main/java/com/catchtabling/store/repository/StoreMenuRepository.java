package com.catchtabling.store.repository;

import com.catchtabling.store.domain.StoreMenu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreMenuRepository extends JpaRepository<StoreMenu, Long> {
}
