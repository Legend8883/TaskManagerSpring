package org.legend8883.taskmanager.tasks.api.dto.requests;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Size;
import org.legend8883.taskmanager.tasks.db.enums.Importance;
import org.legend8883.taskmanager.tasks.db.enums.Status;

import java.time.LocalDateTime;

public record ChangeTaskRequest(
        @Nullable
        @Size(min = 1)
        String title,

        @Nullable
        @Size(min = 1)
        String description,

        @Nullable
        @FutureOrPresent
        LocalDateTime dateTimeWhenYouNeedToComplete,

        @Nullable
        Integer timeToCompleteInMinutes,

        @Nullable
        Importance importance,

        @Nullable
        Status status
) {
}
