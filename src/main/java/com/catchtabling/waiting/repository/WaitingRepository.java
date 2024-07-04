package com.catchtabling.waiting.repository;

import com.catchtabling.waiting.domain.Waiting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WaitingRepository extends JpaRepository<Waiting, Long> {
}
