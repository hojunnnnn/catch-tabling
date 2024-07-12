package com.catchtabling.store.application;

import com.catchtabling.common.exception.customex.ErrorCode;
import com.catchtabling.common.exception.customex.NotFoundException;
import com.catchtabling.store.domain.Store;
import com.catchtabling.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreReader {
    private final StoreRepository storeRepository;

    public Store findStoreById(Long storeId) {
        return storeRepository.findById(storeId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.STORE_NOT_FOUND));
    }
}
