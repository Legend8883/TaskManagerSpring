package org.legend8883.taskmanager.tasks.domain.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.legend8883.taskmanager.tasks.api.dto.requests.ChangeTaskRequest;
import org.legend8883.taskmanager.tasks.api.dto.requests.CreateTaskRequest;
import org.legend8883.taskmanager.tasks.api.dto.responses.TaskResponse;
import org.legend8883.taskmanager.tasks.db.entities.TaskEntity;
import org.legend8883.taskmanager.tasks.db.enums.Status;
import org.legend8883.taskmanager.tasks.db.repositories.TaskRepository;
import org.legend8883.taskmanager.tasks.domain.exceptions.TaskErrorMessages;
import org.legend8883.taskmanager.tasks.domain.mappers.TaskMapper;
import org.legend8883.taskmanager.tasks.domain.services.managers.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskService {
    private final TaskRepository taskRepository;

    private final CreateTaskManager createTaskManager;
    private final GetTaskByIdManager getTaskByIdManager;
    private final GetAllUserTasksManager getAllUserTasksManager;
    private final ChangeTaskManager changeTaskManager;
    private final CompleteTaskManager completeTaskManager;
    private final DeleteTaskByIdManager deleteTaskByIdManager;

    public TaskResponse createNewTask(CreateTaskRequest request) {
        return createTaskManager.create(request);
    }

    public TaskResponse getTaskById(Long id) {
        return getTaskByIdManager.get(id);
    }

    public List<TaskResponse> getAllUserTasks(Integer pageSize, Integer pageNum) {
        return getAllUserTasksManager.get(pageSize, pageNum);
    }

    public TaskResponse changeTask(
            Long id,
            ChangeTaskRequest request
    ) {
        return changeTaskManager.change(id, request);
    }

    public TaskResponse completeTask(Long id) {
        return completeTaskManager.complete(id);
    }

    public void deleteTaskById(Long id) {
        deleteTaskByIdManager.delete(id);
    }
}
