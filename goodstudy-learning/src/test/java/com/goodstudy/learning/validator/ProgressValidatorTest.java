package com.goodstudy.learning.validator;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class ProgressValidatorTest {
    @Test public void validate_ok() { ProgressValidator.validatePercentage(0); ProgressValidator.validatePercentage(100); }
    @Test public void validate_bad() { assertThrows(IllegalArgumentException.class, () -> ProgressValidator.validatePercentage(-5)); }
    @Test public void isComplete() { assertTrue(ProgressValidator.isComplete(100)); assertFalse(ProgressValidator.isComplete(50)); }
}
