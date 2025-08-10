package com.goodstudy.learning.util;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class StringUtilTest {
    @Test public void isBlank_null() { assertTrue(StringUtil.isBlank(null)); }
    @Test public void isBlank_empty() { assertTrue(StringUtil.isBlank("   ")); }
    @Test public void safe_returnsEmpty() { assertEquals("", StringUtil.safe(null)); }
    @Test public void joinWithComma_works() {
        assertEquals("a,b", StringUtil.joinWithComma("a","","b",null));
    }
}
