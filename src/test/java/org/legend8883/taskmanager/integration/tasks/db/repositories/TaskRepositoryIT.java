package org.legend8883.taskmanager.integration.tasks.db.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.legend8883.taskmanager.tasks.db.entities.TaskEntity;
import org.legend8883.taskmanager.tasks.db.repositories.TaskRepository;
import org.legend8883.taskmanager.users.db.entities.UserEntity;
import org.legend8883.taskmanager.users.db.repositories.UserRepository;
import org.legend8883.taskmanager.util.user.UserTestDataFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.legend8883.taskmanager.util.task.TaskEntityTestDataFactory;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TaskRepositoryIT {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    private UserEntity savedUserForTasks;
    private TaskEntity taskEntity1;
    private TaskEntity taskEntity2;

    private UserEntity savedUserForDiffTasks;
    private TaskEntity diffTaskEntity1;
    private TaskEntity diffTaskEntity2;

    @BeforeEach
    void setUp() {
        UserEntity userForSave = UserTestDataFactory.buildUserEntityWithoutId();
        savedUserForTasks = userRepository.save(userForSave);

        taskEntity1 = TaskEntityTestDataFactory.buildTaskEntityWithoutId();
        taskEntity2 = TaskEntityTestDataFactory.buildDifferentTaskEntityWithoutId();
        taskEntity1.setUser(savedUserForTasks);
        taskEntity2.setUser(savedUserForTasks);

        taskRepository.save(taskEntity1);
        taskRepository.save(taskEntity2);


        UserEntity diffUserForSave = UserTestDataFactory.buildDifferentUserEntityWithoutId();
        savedUserForDiffTasks = userRepository.save(diffUserForSave);

        diffTaskEntity1 = TaskEntityTestDataFactory.buildTaskEntityWithoutId();
        diffTaskEntity2 = TaskEntityTestDataFactory.buildDifferentTaskEntityWithoutId();
        diffTaskEntity1.setUser(savedUserForDiffTasks);
        diffTaskEntity2.setUser(savedUserForDiffTasks);

        taskRepository.save(diffTaskEntity1);
        taskRepository.save(diffTaskEntity2);
    }

    @Test
    void findAllByUserIT_shouldReturnUserTasks_whenRightTasksExist() {
        List<TaskEntity> actualUserTasks = taskRepository.findAllByUser(savedUserForTasks, Pageable.unpaged());


        assertThat(actualUserTasks)
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(taskEntity1, taskEntity2);
    }
}