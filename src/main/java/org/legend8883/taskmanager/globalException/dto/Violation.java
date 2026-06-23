package org.legend8883.taskmanager.globalException.dto;

public record Violation(
        String fieldName,
        String message
) {
}
