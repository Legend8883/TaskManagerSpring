package org.legend8883.taskmanager.integration.tasks.api.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.legend8883.taskmanager.tasks.api.dto.requests.ChangeTaskRequest;
import org.legend8883.taskmanager.tasks.api.dto.requests.CreateTaskRequest;
import org.legend8883.taskmanager.tasks.api.dto.responses.TaskResponse;
import org.legend8883.taskmanager.tasks.db.entities.TaskEntity;
import org.legend8883.taskmanager.tasks.db.enums.Status;
import org.legend8883.taskmanager.tasks.db.repositories.TaskRepository;
import org.legend8883.taskmanager.users.db.entities.UserEntity;
import org.legend8883.taskmanager.users.db.repositories.UserRepository;
import org.legend8883.taskmanager.util.task.ChangeTaskRequestTestDataFactory;
import org.legend8883.taskmanager.util.task.CreateTaskRequestTestDataFactory;
import org.legend8883.taskmanager.util.task.TaskEntityTestDataFactory;
import org.legend8883.taskmanager.util.user.UserTestDataFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.legend8883.taskmanager.util.user.UserTestDataFactory.USER_USERNAME;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class TaskControllerSpringBootIT {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    private UserEntity currentUser;

    private final String ASSERTION_ERROR_MESSAGE = "Task not persisted in DB";

    @BeforeEach
    void setUp() {
        UserEntity userForAuth = UserTestDataFactory.buildUserEntityWithoutId();
        currentUser = userRepository.save(userForAuth);
    }

    @Test
    @WithMockUser(username = USER_USERNAME)
    void createNewTaskIT_shouldPersistAndReturnTask_whenRequestIsValid() throws Exception {
        CreateTaskRequest request = CreateTaskRequestTestDataFactory.buildCreateTaskRequest();


        MvcResult result = mockMvc.perform(post("/api/task")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isCreated())
                .andReturn();
        TaskResponse actualResponse = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                TaskResponse.class
        );
        TaskEntity savedTask = taskRepository.findById(actualResponse.id())
                .orElseThrow(() -> new AssertionError(ASSERTION_ERROR_MESSAGE));

        assertThat(savedTask)
                .usingRecursiveComparison()
                .ignoringFields(
                        "id",
                        "user",
                        "status",
                        "createdAt",
                        "updatedAt"
                )
                .isEqualTo(request);

        assertThat(savedTask.getUser().getId())
                .isEqualTo(currentUser.getId());
        assertThat(savedTask.getStatus())
                .isEqualTo(Status.PLANNED);
        assertThat(savedTask.getCreatedAt())
                .isNotNull();
        assertThat(savedTask.getUpdatedAt())
                .isNotNull();
    }

    @Test
    @WithMockUser(username = USER_USERNAME)
    void getTaskByIdIT_shouldReturnTask_whenTaskExists() throws Exception {
        TaskEntity expectedTask = taskRepository.save(TaskEntityTestDataFactory.buildTaskEntityWithoutIdWithUser(currentUser));


        MvcResult result = mockMvc.perform(get("/api/task/{id}", expectedTask.getId()))
                .andExpect(status().isOk())
                .andReturn();
        TaskResponse actualResponse = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                TaskResponse.class
        );

        assertThat(actualResponse)
                .usingRecursiveComparison()
                .isEqualTo(expectedTask);
    }

    @Test
    @WithMockUser(username = USER_USERNAME)
    void getTaskByIdIT_shouldReturnStatus403_whenTaskNotBelongsToUser() throws Exception {
        UserEntity anotherUser = userRepository.save(UserTestDataFactory.buildDifferentUserEntityWithoutId());
        TaskEntity foreignTask = taskRepository.save(TaskEntityTestDataFactory.buildDifferentTaskEntityWithoutIdWithUser(anotherUser));


        mockMvc.perform(get("/api/task/{id}", foreignTask.getId()))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = USER_USERNAME)
    @SuppressWarnings("AssertBetweenInconvertibleTypes")
    void getAllUserTasksIT_shouldReturnUserTasks_whenTasksExist() throws Exception {
        TaskEntity expectedTask1 = TaskEntityTestDataFactory.buildTaskEntityWithoutIdWithUser(currentUser);
        TaskEntity expectedTask2 = TaskEntityTestDataFactory.buildDifferentTaskEntityWithoutIdWithUser(currentUser);
        List<TaskEntity> expectedTasks = List.of(expectedTask1, expectedTask2);
        taskRepository.saveAll(expectedTasks);


        MvcResult result = mockMvc.perform(get("/api/task"))
                .andExpect(status().isOk())
                .andReturn();
        List<TaskResponse> actualResponses = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                new TypeReference<List<TaskResponse>>() {
                }
        );

        assertThat(actualResponses)
                .usingRecursiveFieldByFieldElementComparator()
                .isEqualTo(expectedTasks);
    }

    @Test
    @WithMockUser(username = USER_USERNAME)
    void changeTaskIT_shouldUpdateAndReturnUpdatedTask_whenRequestIsValid() throws Exception {
        TaskEntity taskBeforeChange = taskRepository.save(TaskEntityTestDataFactory.buildTaskEntityWithoutIdWithUser(currentUser));

        ChangeTaskRequest request = ChangeTaskRequestTestDataFactory.buildDifferentChangeTaskRequest();


        MvcResult result = mockMvc.perform(patch("/api/task/{id}", taskBeforeChange.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isOk())
                .andReturn();
        TaskResponse actualResponse = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                TaskResponse.class
        );

        TaskEntity taskAfterChange = taskRepository.findById(actualResponse.id())
                .orElseThrow(() -> new AssertionError(ASSERTION_ERROR_MESSAGE));

        assertThat(taskAfterChange)
                .usingRecursiveComparison()
                .ignoringFields(
                        "id",
                        "user",
                        "createdAt",
                        "updatedAt"
                )
                .isEqualTo(request);
        assertThat(taskAfterChange.getId())
                .isEqualTo(taskBeforeChange.getId());
        assertThat(taskAfterChange.getUser().getId())
                .isEqualTo(currentUser.getId());
        assertThat(taskAfterChange.getCreatedAt())
                .isNotNull();
        assertThat(taskAfterChange.getUpdatedAt())
                .isNotNull();
    }

    @Test
    @WithMockUser(username = USER_USERNAME)
    void completeTaskIT_shouldCompleteAndReturnCompletedTask_whenTaskExists() throws Exception {
        TaskEntity notCompletedTask = taskRepository.save(TaskEntityTestDataFactory.buildTaskEntityWithoutIdWithUser(currentUser));


        MvcResult result = mockMvc.perform(patch("/api/task/complete/{id}", notCompletedTask.getId()))
                .andExpect(status().isOk())
                .andReturn();
        TaskResponse actualResponse = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                TaskResponse.class
        );

        TaskEntity completedTask = taskRepository.findById(actualResponse.id())
                .orElseThrow(() -> new AssertionError(ASSERTION_ERROR_MESSAGE));

        assertThat(completedTask)
                .usingRecursiveComparison()
                .ignoringFields(
                        "status",
                        "updatedAt"
                )
                .isEqualTo(notCompletedTask);
        assertThat(completedTask.getStatus())
                .isEqualTo(Status.FINISHED);
    }

    @Test
    @WithMockUser(username = USER_USERNAME)
    void deleteTaskByIdIT_shouldDeleteTask_whenTaskExists() throws Exception {
        TaskEntity taskToDelete = taskRepository.save(TaskEntityTestDataFactory.buildTaskEntityWithoutIdWithUser(currentUser));
        TaskEntity otherTask = taskRepository.save(TaskEntityTestDataFactory.buildDifferentTaskEntityWithoutIdWithUser(currentUser));


        mockMvc.perform(delete("/api/task/{id}", taskToDelete.getId()))
                .andExpect(status().isOk());

        assertThat(taskRepository.findById(taskToDelete.getId()))
                .isEmpty();
        assertThat(taskRepository.findById(otherTask.getId()))
                .isPresent();
    }
}
