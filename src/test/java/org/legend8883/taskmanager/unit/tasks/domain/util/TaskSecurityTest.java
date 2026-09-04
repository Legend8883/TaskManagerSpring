package org.legend8883.taskmanager.unit.tasks.domain.util;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.legend8883.taskmanager.tasks.db.entities.TaskEntity;
import org.legend8883.taskmanager.tasks.db.repositories.TaskRepository;
import org.legend8883.taskmanager.tasks.domain.exceptions.TaskErrorMessages;
import org.legend8883.taskmanager.tasks.domain.util.TaskSecurity;
import org.legend8883.taskmanager.userDetails.SecurityUser;
import org.legend8883.taskmanager.users.db.entities.UserEntity;
import org.legend8883.taskmanager.users.db.repositories.UserRepository;
import org.legend8883.taskmanager.users.domain.exceptions.UserErrorMessages;
import org.legend8883.taskmanager.util.task.TaskEntityTestDataFactory;
import org.legend8883.taskmanager.util.task.TaskTestFields;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.legend8883.taskmanager.util.task.TaskTestFields.TASK_ID;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskSecurityTest {
    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private TaskSecurity taskSecurity;

    @Test
    void isOwnerTest_shouldReturnTrue_whenTaskBelongsToUser() {
        TaskEntity taskEntity = TaskEntityTestDataFactory.buildTaskEntity();
        UserEntity userEntity = TaskTestFields.TASK_USER;
        String username = userEntity.getUsername();

        when(taskRepository.findById(TASK_ID))
                .thenReturn(Optional.of(taskEntity));
        when(authentication.getName())
                .thenReturn(username);
        when(userRepository.findByUsername(username))
                .thenReturn(Optional.of(userEntity));


        boolean result = taskSecurity.isOwner(TASK_ID, authentication);


        assertThat(result)
                .isTrue();
    }

    @Test
    void isOwnerTest_shouldReturnFalse_whenTaskNotBelongsToUser() {
        TaskEntity taskEntity = TaskEntityTestDataFactory.buildTaskEntity();
        UserEntity userEntityWithOtherTask = TaskTestFields.DIFFERENT_TASK_USER;
        String username = userEntityWithOtherTask.getUsername();

        when(taskRepository.findById(TASK_ID))
                .thenReturn(Optional.of(taskEntity));
        when(authentication.getName())
                .thenReturn(username);
        when(userRepository.findByUsername(username))
                .thenReturn(Optional.of(userEntityWithOtherTask));


        boolean result = taskSecurity.isOwner(TASK_ID, authentication);


        assertThat(result)
                .isFalse();
    }

    @Test
    void isOwnerTest_shouldThrowException_whenTaskNotFound() {
        when(taskRepository.findById(TASK_ID))
                .thenReturn(Optional.empty());


        assertThatThrownBy(() -> taskSecurity.isOwner(TASK_ID, authentication))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage(TaskErrorMessages.taskNotFound(TASK_ID));

        verifyNoInteractions(userRepository);
    }

    @Test
    void isOwnerTest_shouldThrowException_whenUserNotFound() {
        TaskEntity taskEntity = TaskEntityTestDataFactory.buildTaskEntity();
        UserEntity userEntity = TaskTestFields.TASK_USER;
        String username = userEntity.getUsername();

        when(taskRepository.findById(TASK_ID))
                .thenReturn(Optional.of(taskEntity));
        when(authentication.getName())
                .thenReturn(username);
        when(userRepository.findByUsername(username))
                .thenReturn(Optional.empty());


        assertThatThrownBy(() -> taskSecurity.isOwner(TASK_ID, authentication))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessage(UserErrorMessages.userNotFound(username));
    }
}