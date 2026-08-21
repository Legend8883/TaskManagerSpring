package unit.org.legend8883.taskmanager.tasks.domain.services.managers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.legend8883.taskmanager.tasks.api.dto.requests.ChangeTaskRequest;
import org.legend8883.taskmanager.tasks.db.entities.TaskEntity;
import org.legend8883.taskmanager.tasks.domain.util.ChangeTaskUtil;
import util.task.ChangeTaskRequestTestDataFactory;
import util.task.TaskEntityTestDataFactory;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static util.task.TaskTestFields.*;

class ChangeTaskUtilTest {
    private ChangeTaskUtil changeTaskUtil;

    @BeforeEach
    void setUp() {
        changeTaskUtil = new ChangeTaskUtil();
    }

    @Test
    void getChangedTaskEntityTest_shouldReturnSameTaskEntity_whenFieldsNull() {
        TaskEntity originalTaskEntity = TaskEntityTestDataFactory.buildTaskEntity();
        TaskEntity expectedTaskEntity = TaskEntityTestDataFactory.buildTaskEntity();
        ChangeTaskRequest changeTaskRequest = new ChangeTaskRequest(
                null,
                null,
                null,
                null,
                null,
                null
        );


        TaskEntity actualTaskEntity = changeTaskUtil.getChangedTaskEntity(originalTaskEntity, changeTaskRequest);


        assertThat(actualTaskEntity)
                .usingRecursiveComparison()
                .isEqualTo(expectedTaskEntity);
    }

    @ParameterizedTest
    @MethodSource("changeTaskRequestsAndChangedTaskEntities")
    void getChangedTaskEntityTest_shouldReturnChangedTaskEntity_whenSomeFieldsNotNull(
            TaskEntity expectedChangedTaskEntity,
            ChangeTaskRequest changeTaskRequest
    ) {
        TaskEntity originTaskEntity = TaskEntityTestDataFactory.buildTaskEntity();


        TaskEntity actualTaskEntity = changeTaskUtil.getChangedTaskEntity(originTaskEntity, changeTaskRequest);


        assertThat(actualTaskEntity)
                .usingRecursiveComparison()
                .isEqualTo(expectedChangedTaskEntity);
    }

    @Test
    void getChangedTaskEntityTest_shouldReturnChangedTaskEntity_whenAllFieldsNotNull() {
        TaskEntity originalTaskEntity = TaskEntityTestDataFactory.buildTaskEntity();
        TaskEntity expectedTaskEntity = TaskEntityTestDataFactory.buildDifferentTaskEntityWithSameId();
        ChangeTaskRequest difChangedTaskRequest = ChangeTaskRequestTestDataFactory.buildDifferentChangeTaskRequest();


        TaskEntity actualTaskEntity = changeTaskUtil.getChangedTaskEntity(originalTaskEntity, difChangedTaskRequest);


        assertThat(actualTaskEntity)
                .usingRecursiveComparison()
                .isEqualTo(expectedTaskEntity);
    }

    private static Stream<Arguments> changeTaskRequestsAndChangedTaskEntities() {
        return Stream.of(
                Arguments.of(
                        TaskEntityTestDataFactory.buildTaskEntityWithTitle(DIFFERENT_TASK_TITLE),
                        ChangeTaskRequestTestDataFactory.buildChangeTaskRequestWithNullFieldsAndTitle(DIFFERENT_TASK_TITLE)
                ),

                Arguments.of(
                        TaskEntityTestDataFactory.buildTaskEntityWithDescription(DIFFERENT_TASK_DESCRIPTION),
                        ChangeTaskRequestTestDataFactory.buildChangeTaskRequestWithNullFieldsAndDescription(DIFFERENT_TASK_DESCRIPTION)
                ),

                Arguments.of(
                        TaskEntityTestDataFactory.buildTaskEntityWithDateTimeWhenYouNeedToComplete(DIFFERENT_TASK_DATE_TIME_WHEN_YOU_NEED_TO_COMPLETE),
                        ChangeTaskRequestTestDataFactory.buildChangeTaskRequestWithNullFieldsAndDateTimeWhenYouNeedToComplete(DIFFERENT_TASK_DATE_TIME_WHEN_YOU_NEED_TO_COMPLETE)
                ),

                Arguments.of(
                        TaskEntityTestDataFactory.buildTaskEntityWithTimeToCompleteInMinutes(DIFFERENT_TASK_TIME_TO_COMPLETE_IN_MINUTES),
                        ChangeTaskRequestTestDataFactory.buildChangeTaskRequestWithNullFieldsAndTimeToCompleteInMinutes(DIFFERENT_TASK_TIME_TO_COMPLETE_IN_MINUTES)
                ),

                Arguments.of(
                        TaskEntityTestDataFactory.buildTaskEntityWithImportance(DIFFERENT_TASK_IMPORTANCE),
                        ChangeTaskRequestTestDataFactory.buildChangeTaskRequestWithNullFieldsAndImportance(DIFFERENT_TASK_IMPORTANCE)
                ),

                Arguments.of(
                        TaskEntityTestDataFactory.buildTaskEntityWithStatus(DIFFERENT_TASK_STATUS),
                        ChangeTaskRequestTestDataFactory.buildChangeTaskRequestWithNullFieldsAndStatus(DIFFERENT_TASK_STATUS)
                )
        );
    }
}