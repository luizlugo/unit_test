package com.techyourchance.unittestingfundamentals.exercise1;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class NegativeNumberValidatorTest {
    NegativeNumberValidator SUT;

    @Before
    public void setup() {
        SUT = new NegativeNumberValidator();
    }

    @Test
    public void testNegativeNumber() {
        assertThat(SUT.isNegative(-10), is(true));
    }

    @Test
    public void testZeroNumber() {
        assertThat(SUT.isNegative(0), is(false));
    }

    @Test
    public void testPositiveNumber() {
        assertThat(SUT.isNegative(10), is(false));
    }
}