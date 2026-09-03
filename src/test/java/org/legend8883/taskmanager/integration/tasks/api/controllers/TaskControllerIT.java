package org.legend8883.taskmanager.integration.tasks.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.legend8883.taskmanager.globalException.GlobalExceptionHandler;
import org.legend8883.taskmanager.globalException.messages.GlobalErrorMessages;
import org.legend8883.taskmanager.tasks.api.controllers.TaskController;
import org.legend8883.taskmanager.tasks.api.dto.requests.CreateTaskRequest;
import org.legend8883.taskmanager.tasks.api.dto.responses.TaskResponse;
import org.legend8883.taskmanager.tasks.domain.exceptions.TaskErrorMessages;
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
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.legend8883.taskmanager.util.task.TaskTestFields.TASK_ID;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    @MockitoBean(name = "taskSecurity")
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

    @Test
    @WithMockUser
    void createNewTaskIT_shouldReturnCreatedTask_whenRequestIsValid() throws Exception {
        CreateTaskRequest createTaskRequest = CreateTaskRequestTestDataFactory.buildCreateTaskRequest();
        TaskResponse expectedResponse = TaskResponseTestDataFactory.buildTaskResponse();

        when(taskService.createNewTask(createTaskRequest))
                .thenReturn(expectedResponse);


        MvcResult result = mockMvc.perform(post("/api/task")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createTaskRequest))
                )
                .andExpect(status().isCreated())
                .andReturn();

        TaskResponse actualResponse = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                TaskResponse.class
        );

        assertThat(actualResponse)
                .isEqualTo(expectedResponse);
    }

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

    @Test
    @WithMockUser
    void getTaskByIdIT_shouldReturnTask_whenTaskExistsAndBelongsToUser() throws Exception {
        TaskResponse expectedResponse = TaskResponseTestDataFactory.buildTaskResponse();

        isOwnerReturn(true);
        when(taskService.getTaskById(TASK_ID))
                .thenReturn(expectedResponse);


        MvcResult result = mockMvc.perform(get("/api/task/{id}", TASK_ID))
                .andExpect(status().isOk())
                .andReturn();
        TaskResponse actualResponse = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                TaskResponse.class
        );

        assertThat(actualResponse)
                .isEqualTo(expectedResponse);
    }

    @Test
    @WithMockUser
    void getTaskByIdIT_shouldReturnStatus404_whenTaskNotFound() throws Exception {
        isOwnerReturn(true);

        when(taskService.getTaskById(TASK_ID))
                .thenThrow(new EntityNotFoundException(TaskErrorMessages.taskNotFound(TASK_ID)));


        mockMvc.perform(get("/api/task/{id}", TASK_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(GlobalErrorMessages.ENTITY_NOT_FOUND))
                .andExpect(jsonPath("$.exceptionMessage").value(TaskErrorMessages.taskNotFound(TASK_ID)));

        verify(taskSecurity)
                .isOwner(eq(TASK_ID), any());
        verify(taskService)
                .getTaskById(TASK_ID);
    }

    @Test
    @WithMockUser
    void getTaskByIdIT_shouldReturnStatus403_whenTaskNotBelongsToUser() throws Exception {
        isOwnerReturn(false);


        mockMvc.perform(get("/api/task/{id}", TASK_ID))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(GlobalErrorMessages.AUTH_DENIED));

        verify(taskSecurity)
                .isOwner(eq(TASK_ID), any());
        verifyNoInteractions(taskService);
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