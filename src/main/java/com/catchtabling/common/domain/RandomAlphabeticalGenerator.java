package com.catchtabling.common.domain;

import org.apache.commons.lang3.RandomStringUtils;

public class RandomAlphabeticalGenerator implements CodeGenerator {

    @Override
    public String generate(int length) {
        return RandomStringUtils.randomAlphanumeric(length);
    }
}
