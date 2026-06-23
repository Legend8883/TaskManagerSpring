package org.legend8883.taskmanager.users.domain.mappers;

import org.legend8883.taskmanager.users.db.entities.UserEntity;
import org.legend8883.taskmanager.users.api.dto.responses.SimpleUserResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    SimpleUserResponse toSimpleUserResponse(UserEntity user);
}
