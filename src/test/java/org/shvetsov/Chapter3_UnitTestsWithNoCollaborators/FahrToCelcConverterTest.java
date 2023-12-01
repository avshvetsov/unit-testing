package org.shvetsov.Chapter3_UnitTestsWithNoCollaborators;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DisplayName("3.11.5. Fahrenheits to Celcius with Parameterized Tests")
class FahrToCelcConverterTest {

    @ParameterizedTest
    @CsvSource({
            "0,     32",
            "37,    98",
            "100,   212"
    })
    void shouldConvertCelciusToFahrenheit(int celcius, int fahrenheit) {
        assertThat(FahrToCelcConverter.toFahrenheit(celcius)).isEqualTo(fahrenheit);
    }

    @ParameterizedTest
    @CsvSource({
            "0,     32",
            "37,    100",
            "100,   212"
    })
    void shouldConvertFahrenheitToCelcius(int celcius, int fahrenheit) {
        assertThat(FahrToCelcConverter.toCelcius(fahrenheit)).isEqualTo(celcius);
    }

}