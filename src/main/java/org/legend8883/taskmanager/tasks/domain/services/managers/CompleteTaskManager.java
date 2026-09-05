package org.legend8883.taskmanager.tasks.domain.services.managers;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.legend8883.taskmanager.tasks.api.dto.responses.TaskResponse;
import org.legend8883.taskmanager.tasks.db.entities.TaskEntity;
import org.legend8883.taskmanager.tasks.db.enums.Status;
import org.legend8883.taskmanager.tasks.db.repositories.TaskRepository;
import org.legend8883.taskmanager.tasks.domain.exceptions.TaskErrorMessages;
import org.legend8883.taskmanager.tasks.domain.mappers.TaskMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CompleteTaskManager {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public TaskResponse complete(Long id) {
        TaskEntity taskEntity = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(TaskErrorMessages.taskNotFound(id)));

        taskEntity.setStatus(Status.FINISHED);

        TaskEntity savedTask = taskRepository.save(taskEntity);
        log.info("Completed task with id {} ", savedTask.getId());
        return taskMapper.entityToResponse(savedTask);
    }
}
