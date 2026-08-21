package org.legend8883.taskmanager.tasks.domain.services.managers;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.legend8883.taskmanager.tasks.api.dto.requests.ChangeTaskRequest;
import org.legend8883.taskmanager.tasks.api.dto.responses.TaskResponse;
import org.legend8883.taskmanager.tasks.db.entities.TaskEntity;
import org.legend8883.taskmanager.tasks.db.repositories.TaskRepository;
import org.legend8883.taskmanager.tasks.domain.exceptions.TaskErrorMessages;
import org.legend8883.taskmanager.tasks.domain.mappers.TaskMapper;
import org.legend8883.taskmanager.tasks.domain.util.ChangeTaskUtil;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import util.task.ChangeTaskRequestTestDataFactory;
import util.task.TaskEntityTestDataFactory;
import util.task.TaskResponseTestDataFactory;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static util.task.TaskTestFields.TASK_ID;

@ExtendWith(MockitoExtension.class)
class ChangeTaskManagerTest {
    @Mock
    private TaskRepository taskRepository;

    @Mock
    private ChangeTaskUtil changeTaskUtil;

    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private ChangeTaskManager changeTaskManager;

    @Test
    void changeTest_shouldReturnChangedTask_whenRequestFieldsNotNull() {
        ChangeTaskRequest request = ChangeTaskRequestTestDataFactory.buildDifferentChangeTaskRequest();
        TaskEntity originTaskEntity = TaskEntityTestDataFactory.buildTaskEntity();
        TaskEntity changedTaskEntity = TaskEntityTestDataFactory.buildDifferentTaskEntityWithSameId();
        TaskResponse expectedResponse = TaskResponseTestDataFactory.buildDifferentTaskResponse();

        when(taskRepository.findById(TASK_ID))
                .thenReturn(Optional.of(originTaskEntity));
        when(changeTaskUtil.getChangedTaskEntity(originTaskEntity, request))
                .thenReturn(changedTaskEntity);
        when(taskRepository.save(changedTaskEntity))
                .thenReturn(changedTaskEntity);
        when(taskMapper.entityToResponse(changedTaskEntity))
                .thenReturn(expectedResponse);


        TaskResponse actualResponse = changeTaskManager.change(TASK_ID, request);


        assertThat(actualResponse)
                .isEqualTo(expectedResponse);
    }

    @Test
    void changeTest_shouldThrowException_whenTaskNotFound() {
        when(taskRepository.findById(TASK_ID))
                .thenReturn(Optional.empty());


        assertThatThrownBy(() -> changeTaskManager.change(TASK_ID, null))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage(TaskErrorMessages.taskNotFound(TASK_ID));
        verifyNoInteractions(changeTaskUtil, taskMapper);
    }
}