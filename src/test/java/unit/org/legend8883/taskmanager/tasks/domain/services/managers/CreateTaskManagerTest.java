package unit.org.legend8883.taskmanager.tasks.domain.services.managers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.legend8883.taskmanager.tasks.api.dto.requests.CreateTaskRequest;
import org.legend8883.taskmanager.tasks.api.dto.responses.TaskResponse;
import org.legend8883.taskmanager.tasks.db.entities.TaskEntity;
import org.legend8883.taskmanager.tasks.db.repositories.TaskRepository;
import org.legend8883.taskmanager.tasks.domain.mappers.TaskMapper;
import org.legend8883.taskmanager.tasks.domain.services.managers.CreateTaskManager;
import org.legend8883.taskmanager.users.db.entities.UserEntity;
import org.legend8883.taskmanager.users.domain.services.UserManager;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import util.task.CreateTaskRequestTestDataFactory;
import util.task.TaskEntityTestDataFactory;
import util.task.TaskResponseTestDataFactory;
import util.user.UserTestDataFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateTaskManagerTest {
    @Mock
    private UserManager userManager;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private CreateTaskManager createTaskManager;

    @Test
    void createTest_shouldReturnCorrectResponse_whenTaskSaved() {
        CreateTaskRequest request = CreateTaskRequestTestDataFactory.buildCreateTaskRequest();
        UserEntity currentUser = UserTestDataFactory.buildUserEntity();
        TaskEntity taskEntity = TaskEntityTestDataFactory.buildTaskEntity();
        TaskEntity expectedTaskEntity = TaskEntityTestDataFactory.buildTaskEntityForResponse();
        TaskResponse expectedResponse = TaskResponseTestDataFactory.buildTaskResponse();
        ArgumentCaptor<TaskEntity> captor = ArgumentCaptor.forClass(TaskEntity.class);

        when(userManager.getCurrentUser())
                .thenReturn(currentUser);
        when(taskRepository.save(any(TaskEntity.class)))
                .thenReturn(taskEntity);
        when(taskMapper.entityToResponse(taskEntity))
                .thenReturn(expectedResponse);


        TaskResponse actualResponse = createTaskManager.create(request);


        assertThat(actualResponse)
                .isEqualTo(expectedResponse);

        verify(taskRepository).save(captor.capture());
        TaskEntity capturedEntity = captor.getValue();

        assertThat(capturedEntity)
                .usingRecursiveComparison()
                .comparingOnlyFields(
                        "user",
                        "title",
                        "description",
                        "dateTimeWhenYouNeedToComplete",
                        "timeToCompleteInMinutes",
                        "importance"
                ).isEqualTo(expectedTaskEntity);
    }
}