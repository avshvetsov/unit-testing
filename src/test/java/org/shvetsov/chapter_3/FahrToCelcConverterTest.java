package org.shvetsov.chapter_3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class FahrToCelcConverterTest {

    @Test
    @DisplayName("3.11.5. Fahrenheits to Celcius with Parameterized Tests")
    void shouldConvertCelciusToFahrenheit() {
        assertThat(FahrToCelcConverter.toFahrenheit(0)).isEqualTo(32);
        assertThat(FahrToCelcConverter.toFahrenheit(37)).isEqualTo(98);
        assertThat(FahrToCelcConverter.toFahrenheit(100)).isEqualTo(212);
    }

    @Test
    @DisplayName("3.11.5. Fahrenheits to Celcius with Parameterized Tests")
    void shouldConvertFahrenheitToCelcius() {
        assertThat(FahrToCelcConverter.toCelcius(32)).isEqualTo(0);
        assertThat(FahrToCelcConverter.toCelcius(100)).isEqualTo(37);
        assertThat(FahrToCelcConverter.toCelcius(212)).isEqualTo(100);
    }
}