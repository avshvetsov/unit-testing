package org.shvetsov.Chapter4_TestDrivenDevelopment;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * This example requires you to write a method which, given a String, returns a list of all numbers
 * taken from that String that have 3 or more digits.
 */
@DisplayName("4.11.2. Regex")
class RegexTest {

    @ParameterizedTest
    @ValueSource(strings = {"abc 12", "cdefg 345 12bb23", "cdefg 345 12bbb33 678tt"})
    public void regexTest(String input) {

    }
}