package org.legend8883.taskmanager.tasks.domain.util;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.legend8883.taskmanager.tasks.db.entities.TaskEntity;
import org.legend8883.taskmanager.tasks.db.repositories.TaskRepository;
import org.legend8883.taskmanager.tasks.domain.exceptions.TaskErrorMessages;
import org.legend8883.taskmanager.userDetails.SecurityUser;
import org.legend8883.taskmanager.users.db.entities.UserEntity;
import org.legend8883.taskmanager.users.db.repositories.UserRepository;
import org.legend8883.taskmanager.users.domain.exceptions.UserErrorMessages;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import static org.legend8883.taskmanager.globalException.messages.GlobalErrorMessages.USER_NOT_FOUND;

@Component
@RequiredArgsConstructor
public class TaskSecurity {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public boolean isOwner(Long taskId, Authentication authentication) {
        TaskEntity taskEntity = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException(TaskErrorMessages.taskNotFound(taskId)));

        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
        String username = securityUser.getUsername();

        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(UserErrorMessages.userNotFound(username)));

        return taskEntity.getUser().getId().equals(userEntity.getId());
    }
}
