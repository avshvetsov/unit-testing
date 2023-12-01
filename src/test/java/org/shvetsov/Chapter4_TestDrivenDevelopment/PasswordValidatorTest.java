package org.shvetsov.Chapter4_TestDrivenDevelopment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

/**
 * When creating an account, it is often required that the password should fulfil some strength
 * requirements. It should be X characters long, have at least Y digits, contain underscore, hash, and
 * a mixture of lower and capital letters, etc. Your task is to write a method that will validate a given
 * password. The set of rules (requirements) with which you will be verifying the passwords is up to you.
 */
@DisplayName("4.11.1. Password Validator")
class PasswordValidatorTest {

    private PasswordValidator validator;

    @BeforeEach
    void setUp() {
        validator = new PasswordValidator();
    }

    @Test
    public void shouldCreateValidator() {
        assertThat(validator).isNotNull();
    }

    @ParameterizedTest
    @ValueSource(strings = {"1aA!", "a", "qwerty", "asd123", "!"})
    public void shouldThrowException(String input) {
        assertThatExceptionOfType(PasswordValidator.InvalidPasswordException.class).isThrownBy(() -> validator.validate(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"1As12!@Dsa9-df", "Qo1ebcs&aop!"})
    public void shouldValidateSuccessfully(String input) {
        assertThat(validator.validate(input)).isTrue();
    }


}