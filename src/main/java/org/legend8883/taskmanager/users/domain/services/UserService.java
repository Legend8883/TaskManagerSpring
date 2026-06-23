package org.legend8883.taskmanager.users.domain.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.legend8883.taskmanager.users.api.dto.responses.SimpleUserResponse;
import org.legend8883.taskmanager.users.domain.mappers.UserMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserMapper userMapper;
    private final UserManager userManager;

    public SimpleUserResponse getCurrentUserResponse() {
        return userMapper.toSimpleUserResponse(userManager.getCurrentUser());
    }
}
