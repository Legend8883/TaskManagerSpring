package unit.org.legend8883.taskmanager.tasks.domain.services.managers;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.legend8883.taskmanager.tasks.api.dto.responses.TaskResponse;
import org.legend8883.taskmanager.tasks.db.entities.TaskEntity;
import org.legend8883.taskmanager.tasks.db.enums.Status;
import org.legend8883.taskmanager.tasks.db.repositories.TaskRepository;
import org.legend8883.taskmanager.tasks.domain.exceptions.TaskErrorMessages;
import org.legend8883.taskmanager.tasks.domain.mappers.TaskMapper;
import org.legend8883.taskmanager.tasks.domain.services.managers.CompleteTaskManager;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import util.task.TaskEntityTestDataFactory;
import util.task.TaskResponseTestDataFactory;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;
import static util.task.TaskTestFields.TASK_ID;

@ExtendWith(MockitoExtension.class)
class CompleteTaskManagerTest {
    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private CompleteTaskManager completeTaskManager;

    // Статус задачи меняется на FINISHED
    @Test
    void completeTest_shouldReturnFinishedTask_whenTaskExists() {
        TaskEntity originTaskEntity = TaskEntityTestDataFactory.buildTaskEntity();
        TaskEntity finishedTask = TaskEntityTestDataFactory.buildTaskEntityWithStatus(Status.FINISHED);
        TaskResponse expectedResponse = TaskResponseTestDataFactory.buildTaskResponseWithStatus(Status.FINISHED);
        ArgumentCaptor<TaskEntity> captor = ArgumentCaptor.forClass(TaskEntity.class);

        when(taskRepository.findById(TASK_ID))
                .thenReturn(Optional.of(originTaskEntity));
        when(taskRepository.save(any(TaskEntity.class)))
                .thenReturn(finishedTask);
        when(taskMapper.entityToResponse(finishedTask))
                .thenReturn(expectedResponse);


        TaskResponse actualResponse = completeTaskManager.complete(TASK_ID);


        assertThat(actualResponse)
                .isEqualTo(expectedResponse);

        verify(taskRepository).save(captor.capture());
        TaskEntity capturedEntity = captor.getValue();
        assertThat(capturedEntity)
                .usingRecursiveComparison()
                .isEqualTo(originTaskEntity);
    }

    @Test
    void completeTest_shouldThrowException_whenTaskNotFound() {
        when(taskRepository.findById(TASK_ID))
                .thenReturn(Optional.empty());


        assertThatThrownBy(() -> completeTaskManager.complete(TASK_ID))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage(TaskErrorMessages.taskNotFound(TASK_ID));

        verifyNoInteractions(taskMapper);
    }
}