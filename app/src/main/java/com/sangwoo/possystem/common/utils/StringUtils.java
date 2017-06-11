package com.sangwoo.possystem.common.utils;

import java.util.Random;

public final class StringUtils {
    private static final Random random = new Random(System.currentTimeMillis());

    public static String generateFourNumString() {
        String randomString = "";
        for (int i = 0; i < 4; i++)
            randomString += random.nextInt(10);
        return randomString;
    }

    public static boolean isEmpty(String str) {
        return str == null || "".equals(str.trim());
    }

    public static boolean isDigits(String str) {
        if (isEmpty(str))
            return false;

        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c))
                return false;
        }

        return true;
    }
}
