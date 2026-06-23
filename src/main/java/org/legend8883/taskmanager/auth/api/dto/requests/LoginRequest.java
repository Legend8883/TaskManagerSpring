package org.legend8883.taskmanager.auth.api.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequest(
        @NotBlank
        @Size(min = 6)
        String username,

        @NotBlank
        @Size(min = 6)
        String password
) {
}
