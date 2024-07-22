package com.catchtabling.common.domain;

import org.apache.commons.lang3.RandomStringUtils;

public class RandomNumericGenerator implements CodeGenerator {
    @Override
    public String generate(int length) {
        return RandomStringUtils.randomNumeric(length);
    }
}
