package org.legend8883.taskmanager.tasks.api.dto.requests;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.legend8883.taskmanager.tasks.db.enums.Importance;

import java.time.LocalDateTime;

public record CreateTaskRequest(
        @NotBlank
        @Size(min = 1)
        String title,

        @Nullable
        @Size(min = 1)
        String description,

        @NotNull
        @FutureOrPresent
        LocalDateTime dateTimeWhenYouNeedToComplete,

        @Nullable
        Integer timeToCompleteInMinutes,

        @NotNull
        Importance importance
) {
}
