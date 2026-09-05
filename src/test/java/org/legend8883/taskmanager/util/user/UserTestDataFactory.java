package org.legend8883.taskmanager.util.user;

import lombok.experimental.UtilityClass;
import org.legend8883.taskmanager.users.api.dto.responses.SimpleUserResponse;
import org.legend8883.taskmanager.users.db.entities.UserEntity;

@UtilityClass
public final class UserTestDataFactory {
    public static final Long USER_ID = 52L;
    public static final String USER_USERNAME = "Goyda";
    public static final String USER_PASSWORD = "123321";

    public static final Long DIFFERENT_USER_ID = 421L;
    public static final String DIFFERENT_USER_USERNAME = "Gok";
    public static final String DIFFERENT_USER_PASSWORD = "12344321";

    public static UserEntity buildUserEntity() {
        return UserEntity.builder()
                .id(USER_ID)
                .username(USER_USERNAME)
                .password(USER_PASSWORD)
                .build();
    }

    public static UserEntity buildUserEntityWithoutId() {
        return UserEntity.builder()
                .username(USER_USERNAME)
                .password(USER_PASSWORD)
                .build();
    }

    public static SimpleUserResponse buildSimpleUserResponse() {
        return new SimpleUserResponse(
                USER_ID,
                USER_USERNAME
        );
    }

    public static UserEntity buildDifferentUserEntity() {
        return UserEntity.builder()
                .id(DIFFERENT_USER_ID)
                .username(DIFFERENT_USER_USERNAME)
                .password(DIFFERENT_USER_PASSWORD)
                .build();
    }

    public static UserEntity buildDifferentUserEntityWithoutId() {
        return UserEntity.builder()
                .username(DIFFERENT_USER_USERNAME)
                .password(DIFFERENT_USER_PASSWORD)
                .build();
    }

    public static SimpleUserResponse buildDifferentSimpleUserResponse() {
        return new SimpleUserResponse(
                DIFFERENT_USER_ID,
                DIFFERENT_USER_USERNAME
        );
    }
}
