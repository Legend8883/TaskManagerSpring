package org.legend8883.taskmanager.auth.domain.services;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.legend8883.taskmanager.auth.api.dto.requests.LoginRequest;
import org.legend8883.taskmanager.auth.api.dto.requests.RegistrationRequest;
import org.legend8883.taskmanager.auth.api.dto.responses.LoginResponse;
import org.legend8883.taskmanager.users.api.dto.responses.SimpleUserResponse;
import org.legend8883.taskmanager.users.db.entities.UserEntity;
import org.legend8883.taskmanager.users.db.repositories.UserRepository;
import org.legend8883.taskmanager.users.domain.mappers.UserMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final RegistrationManager registrationManager;

    public SimpleUserResponse registration(
            RegistrationRequest request
    ) {
        log.info("Registration attempt");

        registrationManager.checkingUsernameRepeat(request);
        registrationManager.checkingPasswordMatch(request);

        UserEntity userEntity = UserEntity.builder()
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .build();

        UserEntity savedUser = userRepository.save(userEntity);
        log.info("User saved with id: {}", savedUser.getId());
        return userMapper.toSimpleUserResponse(savedUser);
    }

    public LoginResponse login(
            LoginRequest request
    ) {
        log.info("Login attempt");

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                request.username(),
                request.password()
        );

        Authentication authentication = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new LoginResponse(
                "Success login!"
        );
    }

    public void logout(HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        SecurityContextHolder.clearContext();
    }
}
