package com.techyourchance.unittestingfundamentals.exercise3;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import com.techyourchance.unittestingfundamentals.example3.Interval;

import org.junit.Before;
import org.junit.Test;

public class IntervalsAdjacencyDetectorTest {
    IntervalsAdjacencyDetector SUT;

    @Before
    public void setUp() throws Exception {
        SUT = new IntervalsAdjacencyDetector();
    }

    // interval1 before interval2
    @Test
    public void isAdjacent_interval1BeforeInterval2_returnFalse() {
        Interval interval1 = new Interval(1, 10);
        Interval interval2 = new Interval(11, 20);
        Boolean result = SUT.isAdjacent(interval1, interval2);
        assertThat(result, is(false));
    }

    // interval1 starts before interval2 starts
    @Test
    public void isAdjacent_interval1StartsBeforeInterval2Starts_returnFalse() {
        Interval interval1 = new Interval(1, 10);
        Interval interval2 = new Interval(2, 11);
        Boolean result = SUT.isAdjacent(interval1, interval2);
        assertThat(result, is(false));
    }

    // interval1 starts after interval2 starts
    @Test
    public void isAdjacent_interval1StartsAfterInterval2Starts_returnFalse() {
        Interval interval1 = new Interval(3, 10);
        Interval interval2 = new Interval(2, 11);
        Boolean result = SUT.isAdjacent(interval1, interval2);
        assertThat(result, is(false));
    }

    // interval1 ends after interval2 ends
    @Test
    public void isAdjacent_interval1EndsAfterInterval2Ends_returnFalse() {
        Interval interval1 = new Interval(1, 12);
        Interval interval2 = new Interval(2, 11);
        Boolean result = SUT.isAdjacent(interval1, interval2);
        assertThat(result, is(false));
    }

    // interval1 starts adjacently with interval2 ends
    @Test
    public void isAdjacent_interval1StartsAdjacentlyWithInterval2Ends_returnTrue() {
        Interval interval1 = new Interval(12, 17);
        Interval interval2 = new Interval(2, 12);
        Boolean result = SUT.isAdjacent(interval1, interval2);
        assertThat(result, is(true));
    }

    // interval1 ends adjacently with interval2 starts
    @Test
    public void isAdjacent_interval1EndsAdjacentlyWithInterval2Starts_returnTrue() {
        Interval interval1 = new Interval(1, 17);
        Interval interval2 = new Interval(17, 20);
        Boolean result = SUT.isAdjacent(interval1, interval2);
        assertThat(result, is(true));
    }

    // interval1 after interval2
    @Test
    public void isAdjacent_interval1AfterInterval2_returnTrue() {
        Interval interval1 = new Interval(13, 17);
        Interval interval2 = new Interval(2, 12);
        Boolean result = SUT.isAdjacent(interval1, interval2);
        assertThat(result, is(false));
    }

    // interval1 and interval2 are equal
    @Test
    public void isAdjacent_interval1AndInterval2Equal_returnFalse() {
        Interval interval1 = new Interval(1, 12);
        Interval interval2 = new Interval(1, 12);
        Boolean result = SUT.isAdjacent(interval1, interval2);
        assertThat(result, is(false));
    }
}