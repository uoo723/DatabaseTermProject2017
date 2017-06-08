package com.sangwoo.possystem.common.utils;

import org.junit.Test;

import static org.junit.Assert.*;


public class StringUtilsTest {

    @Test
    public void generateFourNumString() throws Exception {
        String randomString = StringUtils.generateFourNumString();
        assertEquals(randomString.length(), 4);
    }
}