package org.legend8883.taskmanager.tasks.domain.services.managers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.legend8883.taskmanager.tasks.api.dto.requests.CreateTaskRequest;
import org.legend8883.taskmanager.tasks.api.dto.responses.TaskResponse;
import org.legend8883.taskmanager.tasks.db.entities.TaskEntity;
import org.legend8883.taskmanager.tasks.db.repositories.TaskRepository;
import org.legend8883.taskmanager.tasks.domain.mappers.TaskMapper;
import org.legend8883.taskmanager.users.db.entities.UserEntity;
import org.legend8883.taskmanager.users.domain.services.UserManager;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CreateTaskManager {
    private final UserManager userManager;
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public TaskResponse create(CreateTaskRequest request) {
        UserEntity currentUser = userManager.getCurrentUser();

        TaskEntity newTask = TaskEntity.builder()
                .user(currentUser)
                .title(request.title())
                .description(request.description())
                .dateTimeWhenYouNeedToComplete(request.dateTimeWhenYouNeedToComplete())
                .timeToCompleteInMinutes(request.timeToCompleteInMinutes())
                .importance(request.importance())
                .build();

        TaskEntity savedTask = taskRepository.save(newTask);
        log.info("Created new task with id {}", savedTask.getId());
        return taskMapper.entityToResponse(savedTask);
    }
}
