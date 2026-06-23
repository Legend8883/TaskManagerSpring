package org.legend8883.taskmanager.users.api.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.legend8883.taskmanager.users.api.dto.responses.SimpleUserResponse;
import org.legend8883.taskmanager.users.domain.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Пользователь")
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @Operation(summary = "Получить текущего пользователя")
    @GetMapping("/me")
    public ResponseEntity<SimpleUserResponse> getCurrentUser() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.getCurrentUserResponse());
    }
}
