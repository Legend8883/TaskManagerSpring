package org.legend8883.taskmanager.tasks.domain.services.managers;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.legend8883.taskmanager.tasks.api.dto.requests.ChangeTaskRequest;
import org.legend8883.taskmanager.tasks.api.dto.responses.TaskResponse;
import org.legend8883.taskmanager.tasks.db.entities.TaskEntity;
import org.legend8883.taskmanager.tasks.db.repositories.TaskRepository;
import org.legend8883.taskmanager.tasks.domain.exceptions.TaskErrorMessages;
import org.legend8883.taskmanager.tasks.domain.mappers.TaskMapper;
import org.legend8883.taskmanager.tasks.domain.util.ChangeTaskUtil;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ChangeTaskManager {
    private final TaskRepository taskRepository;
    private final ChangeTaskUtil changeTaskUtil;
    private final TaskMapper taskMapper;

    public TaskResponse change(
            Long id,
            ChangeTaskRequest request
    ) {
        TaskEntity taskEntity = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(TaskErrorMessages.taskNotFound(id)));

        TaskEntity changedTaskEntity = changeTaskUtil.getChangedTaskEntity(taskEntity, request);

        TaskEntity savedTask = taskRepository.save(changedTaskEntity);
        log.info("Changed task with id {}", savedTask.getId());
        return taskMapper.entityToResponse(savedTask);
    }
}
