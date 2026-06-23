package org.legend8883.taskmanager.tasks.api.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.legend8883.taskmanager.tasks.api.dto.requests.ChangeTaskRequest;
import org.legend8883.taskmanager.tasks.api.dto.requests.CreateTaskRequest;
import org.legend8883.taskmanager.tasks.api.dto.responses.TaskResponse;
import org.legend8883.taskmanager.tasks.domain.services.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Управление задачами")
@RestController
@RequestMapping("/api/task")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @Operation(summary = "Создание задачи")
    @PostMapping
    public ResponseEntity<TaskResponse> createNewTask(
            @Valid @RequestBody CreateTaskRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(taskService.createNewTask(request));
    }

    @Operation(summary = "Получить задачу по id")
    @GetMapping("/{id}")
    @PreAuthorize("@taskSecurity.isOwner(#id, authentication)")
    public ResponseEntity<TaskResponse> getTaskById(
            @Parameter(description = "Id задачи")
            @PathVariable Long id
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(taskService.getTaskById(id));
    }

    @Operation(summary = "Получить все задачи текущего пользователя")
    @GetMapping
    public ResponseEntity<List<TaskResponse>> getAllUserTasks(
            @RequestParam(name = "pageSize", required = false) Integer pageSize,
            @RequestParam(name = "pageNum", required = false) Integer pageNum
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(taskService.getAllUserTasks(pageSize, pageNum));
    }

    @Operation(summary = "Изменить задачу")
    @PatchMapping("/{id}")
    @PreAuthorize("@taskSecurity.isOwner(#id, authentication)")
    public ResponseEntity<TaskResponse> changeTask(
            @Parameter(description = "Id задачи")
            @PathVariable Long id,

            @Valid @RequestBody ChangeTaskRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(taskService.changeTask(id, request));
    }

    @Operation(summary = "Поставить статус ВЫПОЛНЕНА")
    @PatchMapping("/complete/{id}")
    @PreAuthorize("@taskSecurity.isOwner(#id, authentication)")
    public ResponseEntity<TaskResponse> completeTask(
            @Parameter(description = "Id задачи")
            @PathVariable Long id
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(taskService.completeTask(id));
    }

    @Operation(summary = "Удалить задачу")
    @DeleteMapping("/{id}")
    @PreAuthorize("@taskSecurity.isOwner(#id, authentication)")
    public ResponseEntity<Void> deleteTaskById(
            @Parameter(description = "Id задачи")
            @PathVariable Long id
    ) {
        taskService.deleteTaskById(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}
