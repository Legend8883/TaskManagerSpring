package org.legend8883.taskmanager.tasks.api.dto.responses;

import org.legend8883.taskmanager.tasks.db.enums.Importance;
import org.legend8883.taskmanager.tasks.db.enums.Status;
import org.legend8883.taskmanager.users.api.dto.responses.SimpleUserResponse;

import java.time.LocalDateTime;

public record TaskResponse(
        Long id,

        SimpleUserResponse user,

        String title,

        String description,

        LocalDateTime dateTimeWhenYouNeedToComplete,

        Integer timeToCompleteInMinutes,

        Importance importance,

        Status status,

        LocalDateTime createdAt,

        LocalDateTime updatedAt
) {
}
