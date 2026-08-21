package unit.org.legend8883.taskmanager.tasks.domain.services.managers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.legend8883.taskmanager.tasks.db.repositories.TaskRepository;
import org.legend8883.taskmanager.tasks.domain.services.managers.DeleteTaskByIdManager;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;
import static util.task.TaskTestFields.TASK_ID;

@ExtendWith(MockitoExtension.class)
class DeleteTaskByIdManagerTest {
    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private DeleteTaskByIdManager deleteTaskByIdManager;

    @Test
    void deleteTest_shouldCallDeleteById_whenDeleteTask() {
        deleteTaskByIdManager.delete(TASK_ID);


        verify(taskRepository).deleteById(TASK_ID);
    }
}