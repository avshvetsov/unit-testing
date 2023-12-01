package org.shvetsov.Chapter3_UnitTestsWithNoCollaborators;

/**
 * Chapter 3. Unit Tests with no Collaborators
 * <p>3.11.5. Fahrenheits to Celcius with Parameterized Tests
 */
public class FahrToCelcConverter {

    public static int toFahrenheit(int celcius) {
        return celcius * 9 / 5 + 32;
    }

    public static int toCelcius(int fahrenheit) {
        return (fahrenheit - 32) * 5 / 9;
    }
}
