package unit.org.legend8883.taskmanager.tasks.domain.services.managers;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.legend8883.taskmanager.tasks.api.dto.responses.TaskResponse;
import org.legend8883.taskmanager.tasks.db.entities.TaskEntity;
import org.legend8883.taskmanager.tasks.db.repositories.TaskRepository;
import org.legend8883.taskmanager.tasks.domain.exceptions.TaskErrorMessages;
import org.legend8883.taskmanager.tasks.domain.mappers.TaskMapper;
import org.legend8883.taskmanager.tasks.domain.services.managers.GetTaskByIdManager;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import util.task.TaskEntityTestDataFactory;
import util.task.TaskResponseTestDataFactory;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static util.task.TaskTestFields.TASK_ID;

@ExtendWith(MockitoExtension.class)
class GetTaskByIdManagerTest {
    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private GetTaskByIdManager getTaskByIdManager;

    @Test
    void getTest_shouldReturnResponse_whenTaskExists() {
        TaskEntity taskEntity = TaskEntityTestDataFactory.buildTaskEntity();
        TaskResponse expectedResponse = TaskResponseTestDataFactory.buildTaskResponse();

        when(taskRepository.findById(TASK_ID))
                .thenReturn(Optional.of(taskEntity));
        when(taskMapper.entityToResponse(taskEntity))
                .thenReturn(expectedResponse);


        TaskResponse actualResponse = getTaskByIdManager.get(TASK_ID);


        assertThat(actualResponse)
                .isEqualTo(expectedResponse);
    }

    @Test
    void getTest_shouldThrowException_whenTaskNotFound() {
        when(taskRepository.findById(TASK_ID))
                .thenReturn(Optional.empty());


        assertThatThrownBy(() -> getTaskByIdManager.get(TASK_ID))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage(TaskErrorMessages.taskNotFound(TASK_ID));

        verifyNoInteractions(taskMapper);
    }
}