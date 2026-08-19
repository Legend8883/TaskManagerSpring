package org.legend8883.taskmanager.auth.domain.exceptions;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class AuthErrorMessages {
    public static final String DIFFERENT_PASSWORDS = "Passwords in registration don't match";
    public static final String REPEATED_USERNAME = "Username is already in use";
}
