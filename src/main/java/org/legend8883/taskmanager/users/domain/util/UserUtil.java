package org.legend8883.taskmanager.users.domain.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.legend8883.taskmanager.users.db.entities.UserEntity;
import org.legend8883.taskmanager.users.db.repositories.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserUtil {
    private final UserRepository userRepository;

    @PreAuthorize("isAuthenticated()")
    public UserEntity getCurrentUser() {
        log.debug("Getting current user");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username " + username + " not found."));
    }
}
