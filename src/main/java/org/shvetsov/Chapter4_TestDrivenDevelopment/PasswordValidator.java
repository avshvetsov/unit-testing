package org.shvetsov.Chapter4_TestDrivenDevelopment;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PasswordValidator {


    public boolean validate(String input) {
        Set<String> exceptionMessages = new HashSet<>(Set.of("Password must contains 6 or more symbols"
                , "Password must contains digit"
                , "Password must contains capital letter"
                , "Password must contains lower case letter"
                , "Password must contains special symbols"));
        if (input.length() >= 6) {
            exceptionMessages.remove("Password must contains 6 or more symbols");
        }

        for (int i = 0; i < input.length(); i++) {
            if (Character.isDigit(input.charAt(i))) exceptionMessages.remove("Password must contains digit");
            if (Character.isUpperCase(input.charAt(i))) exceptionMessages.remove("Password must contains capital letter");
            if (Character.isLowerCase(input.charAt(i))) exceptionMessages.remove("Password must contains lower case letter");
            if (List.of('!', '@', '&', '#', '$').contains(input.charAt(i))) exceptionMessages.remove("Password must contains special symbols");
        }
        if (!exceptionMessages.isEmpty()) {
            throw new InvalidPasswordException(String.join(", ", exceptionMessages));
        }
        return true;
    }

    public static class InvalidPasswordException extends RuntimeException {
        public InvalidPasswordException(String message) {
            super(message);
        }
    }
}
