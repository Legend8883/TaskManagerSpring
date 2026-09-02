package org.legend8883.taskmanager.integration.tasks.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.legend8883.taskmanager.globalException.GlobalExceptionHandler;
import org.legend8883.taskmanager.tasks.api.controllers.TaskController;
import org.legend8883.taskmanager.tasks.api.dto.requests.CreateTaskRequest;
import org.legend8883.taskmanager.tasks.api.dto.responses.TaskResponse;
import org.legend8883.taskmanager.tasks.domain.services.TaskService;
import org.legend8883.taskmanager.tasks.domain.util.TaskSecurity;
import org.legend8883.taskmanager.util.task.CreateTaskRequestTestDataFactory;
import org.legend8883.taskmanager.util.task.TaskResponseTestDataFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import static org.legend8883.taskmanager.util.task.TaskTestFields.TASK_ID;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
@Import({GlobalExceptionHandler.class, TaskControllerIT.MethodSecurityConfig.class})
class TaskControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private TaskSecurity taskSecurity;

    @MockitoBean
    private TaskService taskService;

    @TestConfiguration
    @EnableMethodSecurity
    static class MethodSecurityConfig {
        @Bean
        SecurityFilterChain testFilterChain(HttpSecurity http) throws Exception {
            http
                    .csrf(AbstractHttpConfigurer::disable)
                    .authorizeHttpRequests(
                            auth -> auth
                                    .anyRequest().authenticated()
                    );

            return http.build();
        }
    }

    // Happy path
    @Test
    @WithMockUser
    void createNewTaskIT_shouldReturnCreatedTask_whenRequestIsValid() throws Exception {
        CreateTaskRequest createTaskRequest = CreateTaskRequestTestDataFactory.buildCreateTaskRequest();
        TaskResponse expectedResponse = TaskResponseTestDataFactory.buildTaskResponse();

        isOwnerReturn(true);
        when(taskService.createNewTask(createTaskRequest))
                .thenReturn(expectedResponse);


        mockMvc.perform(post("/api/task")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createTaskRequest))
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(expectedResponse.id()))
//                .andExpect(jsonPath("$.user").value(taskResponse.user()))
                .andExpect(jsonPath("$.title").value(expectedResponse.title()))
                .andExpect(jsonPath("$.description").value(expectedResponse.description()))
                .andExpect(jsonPath("$.dateTimeWhenYouNeedToComplete").value(expectedResponse.dateTimeWhenYouNeedToComplete()))
                .andExpect(jsonPath("$.timeToCompleteInMinutes").value(expectedResponse.timeToCompleteInMinutes()))
                .andExpect(jsonPath("$.importance").value(expectedResponse.importance()))
                .andExpect(jsonPath("$.status").value(expectedResponse.status()));
    }

    // Not valid
    @ParameterizedTest
    @MethodSource("invalidCreateTaskRequests")
    @WithMockUser
    void createNewTaskIT_shouldReturnStatus400_whenTaskNotValid(
            CreateTaskRequest invalidRequest,
            String expectedFieldName
    ) throws Exception {
        mockMvc.perform(post("/api/task")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest))
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.violations[0].fieldName").value(expectedFieldName));

        verifyNoInteractions(taskService);
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

    private void isOwnerReturn(boolean returnValue) {
        when(taskSecurity.isOwner(eq(TASK_ID), any()))
                .thenReturn(returnValue);
    }

    private static Stream<Arguments> invalidCreateTaskRequests() {
        return Stream.of(
                Arguments.of(
                        CreateTaskRequestTestDataFactory.buildCreateTaskRequestWithTitle("ab"),
                        "title"
                ),
                Arguments.of(
                        CreateTaskRequestTestDataFactory.buildCreateTaskRequestWithDescription("ab"),
                        "description"
                ),
                Arguments.of(
                        CreateTaskRequestTestDataFactory.buildCreateTaskRequestWithDateTimeWhenYouNeedToComplete(LocalDateTime.of(2025, 9, 2, 10, 0)),
                        "dateTimeWhenYouNeedToComplete"
                ),
                Arguments.of(
                        CreateTaskRequestTestDataFactory.buildCreateTaskRequestWithImportance(null),
                        "importance"
                )
        );
    }
}