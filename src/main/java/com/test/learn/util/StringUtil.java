package com.test.learn.util;

public class StringUtil {

    public void checkNonEmptyString(String str) {
        if (str == null || str.trim().length() == 0) {
            throw new RuntimeException("empty string");
        }
    }
}
