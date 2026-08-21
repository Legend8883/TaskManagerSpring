package unit.org.legend8883.taskmanager.tasks.domain.services.managers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.legend8883.taskmanager.tasks.api.dto.responses.TaskResponse;
import org.legend8883.taskmanager.tasks.db.entities.TaskEntity;
import org.legend8883.taskmanager.tasks.db.repositories.TaskRepository;
import org.legend8883.taskmanager.tasks.domain.mappers.TaskMapper;
import org.legend8883.taskmanager.tasks.domain.services.managers.GetAllUserTasksManager;
import org.legend8883.taskmanager.users.db.entities.UserEntity;
import org.legend8883.taskmanager.users.domain.util.UserUtil;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import util.task.TaskEntityTestDataFactory;
import util.task.TaskResponseTestDataFactory;
import util.task.TaskTestFields;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetAllUserTasksManagerTest {
    @Mock
    private UserUtil userUtil;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private GetAllUserTasksManager getAllUserTasksManager;

    @Test
    void getTest_shouldReturnResponseList_whenUserTasksExist() {
        UserEntity currentUser = TaskTestFields.TASK_USER;
        TaskEntity taskEntity1 = TaskEntityTestDataFactory.buildTaskEntity();
        TaskEntity taskEntity2 = TaskEntityTestDataFactory.buildDifferentTaskEntity();
        List<TaskEntity> taskEntities = List.of(taskEntity1, taskEntity2);

        TaskResponse taskResponse1 = TaskResponseTestDataFactory.buildTaskResponse();
        TaskResponse taskResponse2 = TaskResponseTestDataFactory.buildDifferentTaskResponse();
        List<TaskResponse> expectedResponses = List.of(taskResponse1, taskResponse2);

        when(userUtil.getCurrentUser())
                .thenReturn(currentUser);
        when(taskRepository.findAllByUser(eq(currentUser), any(Pageable.class)))
                .thenReturn(taskEntities);
        when(taskMapper.entitiesToResponses(taskEntities))
                .thenReturn(expectedResponses);


        List<TaskResponse> actualResponses = getAllUserTasksManager.get(null, null);


        assertThat(actualResponses)
                .isEqualTo(expectedResponses);
    }

    @Test
    void getTest_shouldReturnEmptyResponseList_whenUserTasksNotFound() {
        UserEntity currentUser = TaskTestFields.TASK_USER;

        when(userUtil.getCurrentUser())
                .thenReturn(currentUser);
        when(taskRepository.findAllByUser(eq(currentUser), any(Pageable.class)))
                .thenReturn(Collections.emptyList());
        when(taskMapper.entitiesToResponses(Collections.emptyList()))
                .thenReturn(Collections.emptyList());


        List<TaskResponse> actualResponseList = getAllUserTasksManager.get(null, null);


        assertThat(actualResponseList)
                .isEmpty();
    }
}