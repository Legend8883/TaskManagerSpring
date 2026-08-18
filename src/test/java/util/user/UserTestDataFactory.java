package util.user;

import lombok.experimental.UtilityClass;
import org.legend8883.taskmanager.users.api.dto.responses.SimpleUserResponse;
import org.legend8883.taskmanager.users.db.entities.UserEntity;

@UtilityClass
public final class UserTestDataFactory {
    public static final Long USER_ID = 52L;
    public static final String USER_USERNAME = "Goyda";
    public static final String USER_PASSWORD = "123321";

    public static UserEntity buildUserEntity() {
        return UserEntity.builder()
                .id(USER_ID)
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
}
