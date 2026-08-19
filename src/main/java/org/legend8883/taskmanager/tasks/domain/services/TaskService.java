package org.legend8883.taskmanager.tasks.domain.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.legend8883.taskmanager.misc.PageableCreator;
import org.legend8883.taskmanager.tasks.api.dto.requests.ChangeTaskRequest;
import org.legend8883.taskmanager.tasks.api.dto.requests.CreateTaskRequest;
import org.legend8883.taskmanager.tasks.api.dto.responses.TaskResponse;
import org.legend8883.taskmanager.tasks.db.entities.TaskEntity;
import org.legend8883.taskmanager.tasks.db.enums.Status;
import org.legend8883.taskmanager.tasks.db.repositories.TaskRepository;
import org.legend8883.taskmanager.tasks.domain.mappers.TaskMapper;
import org.legend8883.taskmanager.tasks.domain.services.managers.ChangeTaskManager;
import org.legend8883.taskmanager.tasks.domain.services.managers.CreateTaskManager;
import org.legend8883.taskmanager.users.db.entities.UserEntity;
import org.legend8883.taskmanager.users.domain.services.UserManager;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserManager userManager;
    private final TaskMapper taskMapper;
    private final PageableCreator pageableCreator;

    private final CreateTaskManager createTaskManager;
    private final ChangeTaskManager changeTaskManager;

    public TaskResponse createNewTask(CreateTaskRequest request) {
        return createTaskManager.create(request);
    }

    public TaskResponse getTaskById(Long id) {
        TaskEntity taskEntity = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task with id " + id + " not found"));

        return taskMapper.entityToResponse(taskEntity);
    }

    public List<TaskResponse> getAllUserTasks(Integer pageSize, Integer pageNum) {
        UserEntity currentUser = userManager.getCurrentUser();

        Pageable pageable = pageableCreator.assemble(pageSize, pageNum);

        List<TaskEntity> userTasks = taskRepository.findAllByUser(currentUser, pageable);

        return taskMapper.entitiesToResponses(userTasks);
    }

    public TaskResponse changeTask(
            Long id,
            ChangeTaskRequest request
    ) {
        TaskEntity taskEntity = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task with id " + id + " not found"));

        TaskEntity changedTaskEntity = changeTaskManager.getChangedTaskEntity(taskEntity, request);

        TaskEntity savedTask = taskRepository.save(changedTaskEntity);
        log.info("Changed task with id {} ", savedTask.getId());
        return taskMapper.entityToResponse(savedTask);
    }

    public TaskResponse completeTask(Long id) {
        TaskEntity taskEntity = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task with id " + id + " not found"));

        taskEntity.setStatus(Status.FINISHED);

        TaskEntity savedTask = taskRepository.save(taskEntity);
        log.info("Completed task with id {} ", savedTask.getId());
        return taskMapper.entityToResponse(savedTask);
    }

    public void deleteTaskById(Long id) {
        taskRepository.deleteById(id);
        log.info("Deleted task with id {}", id);
    }
}
