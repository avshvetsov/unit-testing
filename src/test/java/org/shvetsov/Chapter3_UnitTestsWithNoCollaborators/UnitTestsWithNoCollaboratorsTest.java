package org.shvetsov.Chapter3_UnitTestsWithNoCollaborators;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.Base64;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.shvetsov.Chapter3_UnitTestsWithNoCollaborators.UnitTestsWithNoCollaborators.reverse;

class UnitTestsWithNoCollaboratorsTest {

    @Test
    @DisplayName("3.11.2. Learn About AssertJ")
    void learnAssertJ() {
        double dbl = 2.5;
        String str = "Hello";
        BigDecimal bigDecimal = BigDecimal.ZERO;
        List<Object> list = List.of(1, "qwerty", 'a');

        assertThat(dbl).isCloseTo(5.0, within(3.0));
        assertThat(Base64.getEncoder().encodeToString(str.getBytes())).isBase64();
        assertThat(bigDecimal).isLessThanOrEqualTo(BigDecimal.ONE);
        assertThat(list).contains("qwerty");
    }

    @DisplayName("3.11.3. String Reverse")
    @ParameterizedTest
    @ValueSource(strings = {"", "abc", "ssssss", "iqugfiubduifpaibqqfbwiubef", "   ", "gsd fgsd fgsd  "})
    void reverseTest(String str) {
        assertThat(reverse(str)).isEqualTo(new StringBuilder(str).reverse().toString());
    }

    @DisplayName("3.11.3. String Reverse")
    @ParameterizedTest
    @NullSource
    void shouldThrowException(String str) {
        assertThatExceptionOfType(NullPointerException.class).isThrownBy(() -> reverse(str));
    }

}