package com.prakem.prakem.util;

import com.prakem.prakem.exceptions.PasswordValidationException;

import java.util.regex.Pattern;

public class PasswordValidator {

    private static final String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,20}$";
    private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

    public static void validate(String password) {
        if (!password.matches(PASSWORD_PATTERN)) {
            throw new PasswordValidationException("Password must contain at least one lowercase letter, one uppercase letter, one number, and one special character");
        }
        if (password.contains(" ")) {
            throw new PasswordValidationException("The password cannot contain spaces");
        }
    }
}
