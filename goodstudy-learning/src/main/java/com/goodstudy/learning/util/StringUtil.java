package com.goodstudy.learning.util;

public class StringUtil {
    public static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }

    public static String safe(String s) {
        return s == null ? "" : s;
    }

    public static String joinWithComma(String... parts) {
        StringBuilder sb = new StringBuilder();
        for (String p : parts) {
            if (p != null && !p.isEmpty()) {
                if (sb.length() > 0) sb.append(",");
                sb.append(p.trim());
            }
        }
        return sb.toString();
    }
}
