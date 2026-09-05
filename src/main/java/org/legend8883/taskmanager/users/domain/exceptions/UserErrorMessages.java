package org.legend8883.taskmanager.users.domain.exceptions;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class UserErrorMessages {
    private static final String USER_NOT_FOUND = "User with username %s not found.";

    public static String userNotFound(String username) {
        return String.format(USER_NOT_FOUND, username);
    }
}
