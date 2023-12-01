package org.shvetsov.Chapter4_TestDrivenDevelopment;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This example requires you to write a method which, given a String, returns a list of all numbers
 * taken from that String that have 3 or more digits.
 */
@DisplayName("4.11.2. Regex")
class RegexTest {

    private final Regex regex = new Regex();

    @ParameterizedTest
    @ValueSource(strings = {"abc 12", "cdefg 3450 12bb23", "cdefg 345 12bbb33 678tt"})
    public void regexTest(String input) {
        Pattern p = Pattern.compile("\\d{3,}+");
        Matcher m = p.matcher(input);
        List<String> correctResult = new ArrayList<>();
        while(m.find()) {
            correctResult.add(m.group());
        }

        assertThat(regex.getThreeOrMoreDigitNumbersFromString(input)).hasSameElementsAs(correctResult);
    }
}