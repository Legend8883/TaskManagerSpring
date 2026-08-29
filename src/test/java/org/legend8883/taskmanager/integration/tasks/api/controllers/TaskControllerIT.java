package org.legend8883.taskmanager.integration.tasks.api.controllers;

import org.junit.jupiter.api.Test;
import org.legend8883.taskmanager.globalException.GlobalExceptionHandler;
import org.legend8883.taskmanager.tasks.api.controllers.TaskController;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;

@WebMvcTest(TaskController.class)
@Import(GlobalExceptionHandler.class)
class TaskControllerIT {

    // Happy path
    @Test
    void createNewTaskIT_shouldReturnResponse_whenTaskCreated() {

    }

    // Happy path
    @Test
    void getTaskById() {
    }

    // Happy path
    @Test
    void getAllUserTasks() {
    }

    // Happy path
    @Test
    void changeTask() {
    }

    // Happy path
    @Test
    void completeTask() {
    }

    // Happy path
    @Test
    void deleteTaskById() {
    }
}