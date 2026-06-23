package org.legend8883.taskmanager.auth.api.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.legend8883.taskmanager.auth.api.dto.requests.LoginRequest;
import org.legend8883.taskmanager.auth.api.dto.requests.RegistrationRequest;
import org.legend8883.taskmanager.auth.api.dto.responses.LoginResponse;
import org.legend8883.taskmanager.auth.domain.services.AuthService;
import org.legend8883.taskmanager.users.api.dto.responses.SimpleUserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Аутентификация")
public class AuthController {
    private final AuthService authService;

    @Operation(summary = "Регистрация нового пользователя")
    @PostMapping("/register")
    public ResponseEntity<SimpleUserResponse> registration(
            @Valid @RequestBody RegistrationRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(authService.registration(request));
    }

    @Operation(summary = "Логин")
    @PostMapping
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(authService.login(request));
    }

    @Operation(summary = "Выход из аккаунта")
    @DeleteMapping
    public ResponseEntity<Void> logout(
            HttpServletRequest httpServletRequest
    ) {
        authService.logout(httpServletRequest);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}
