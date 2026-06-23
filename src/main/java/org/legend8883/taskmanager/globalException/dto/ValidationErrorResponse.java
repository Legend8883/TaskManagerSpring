package org.legend8883.taskmanager.globalException.dto;

import java.util.List;

public record ValidationErrorResponse(
        List<Violation> violations
) {
}
