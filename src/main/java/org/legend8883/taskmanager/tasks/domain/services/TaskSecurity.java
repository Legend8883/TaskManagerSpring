package org.legend8883.taskmanager.tasks.domain.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.legend8883.taskmanager.tasks.db.entities.TaskEntity;
import org.legend8883.taskmanager.tasks.db.repositories.TaskRepository;
import org.legend8883.taskmanager.userDetails.SecurityUser;
import org.legend8883.taskmanager.users.db.entities.UserEntity;
import org.legend8883.taskmanager.users.db.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TaskSecurity {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public boolean isOwner(Long taskId, Authentication authentication) {
        TaskEntity taskEntity = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task with id: " + taskId + " not found!"));

        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();

        UserEntity userEntity = userRepository.findByUsername(securityUser.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));

        return taskEntity.getUser().getId().equals(userEntity.getId());
    }
}
