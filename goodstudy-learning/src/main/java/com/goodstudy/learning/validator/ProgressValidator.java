package com.goodstudy.learning.validator;

public class ProgressValidator {
    public static void validatePercentage(int p) {
        if (p < 0 || p > 100) throw new IllegalArgumentException("Invalid percentage: " + p);
    }

    public static boolean isComplete(int p) { return p >= 100; }
}
