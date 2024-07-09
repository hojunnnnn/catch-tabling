package com.catchtabling.store.application;

import com.catchtabling.store.domain.Store;
import com.catchtabling.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class StoreReader {
    private final StoreRepository storeRepository;

    public Store findStoreById(Long storeId) {
        return storeRepository.findById(storeId)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 가게입니다."));
    }
}
