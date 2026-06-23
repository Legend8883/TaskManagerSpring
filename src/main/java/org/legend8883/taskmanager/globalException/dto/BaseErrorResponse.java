package org.legend8883.taskmanager.globalException.dto;

import java.time.LocalDateTime;

public record BaseErrorResponse(
        String message,

        String exceptionMessage,

        LocalDateTime errorTime
) {
}
