package org.legend8883.taskmanager.auth.domain.services;

import lombok.RequiredArgsConstructor;
import org.legend8883.taskmanager.auth.api.dto.requests.RegistrationRequest;
import org.legend8883.taskmanager.auth.domain.exceptions.AuthException;
import org.legend8883.taskmanager.users.db.repositories.UserRepository;
import org.springframework.stereotype.Component;

import static org.legend8883.taskmanager.auth.domain.exceptions.AuthErrorMessages.DIFFERENT_PASSWORDS;
import static org.legend8883.taskmanager.auth.domain.exceptions.AuthErrorMessages.REPEATED_USERNAME;

@Component
@RequiredArgsConstructor
public class RegistrationManager {
    private final UserRepository userRepository;

    public void checkingPasswordMatch(RegistrationRequest request) {
        String originalPassword = request.password();
        String confirmPassword = request.confirmPassword();

        if (!originalPassword.equals(confirmPassword)) {
            throw new AuthException(DIFFERENT_PASSWORDS);
        }
    }

    public void checkingUsernameRepeat(RegistrationRequest request) {
        String username = request.username();

        if (userRepository.existsByUsername(username)) {
            throw new AuthException(REPEATED_USERNAME);
        }
    }
}
