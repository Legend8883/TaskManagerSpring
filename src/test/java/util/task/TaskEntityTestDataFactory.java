package util.task;

import lombok.experimental.UtilityClass;
import org.legend8883.taskmanager.tasks.db.entities.TaskEntity;
import org.legend8883.taskmanager.tasks.db.enums.Importance;
import org.legend8883.taskmanager.tasks.db.enums.Status;

import java.time.LocalDateTime;

import static util.task.TaskTestFields.*;

@UtilityClass
public final class TaskEntityTestDataFactory {
    public static TaskEntity buildTaskEntity() {
        return TaskEntity.builder()
                .id(TASK_ID)
                .user(TASK_USER)
                .title(TASK_TITLE)
                .description(TASK_DESCRIPTION)
                .dateTimeWhenYouNeedToComplete(TASK_DATE_TIME_WHEN_YOU_NEED_TO_COMPLETE)
                .timeToCompleteInMinutes(TASK_TIME_TO_COMPLETE_IN_MINUTES)
                .importance(TASK_IMPORTANCE)
                .status(TASK_STATUS)
                .build();
    }

    public static TaskEntity buildTaskEntityWithoutId() {
        return TaskEntity.builder()
                .user(TASK_USER)
                .title(TASK_TITLE)
                .description(TASK_DESCRIPTION)
                .dateTimeWhenYouNeedToComplete(TASK_DATE_TIME_WHEN_YOU_NEED_TO_COMPLETE)
                .timeToCompleteInMinutes(TASK_TIME_TO_COMPLETE_IN_MINUTES)
                .importance(TASK_IMPORTANCE)
                .status(TASK_STATUS)
                .build();
    }

    public static TaskEntity buildTaskEntityForCaptor() {
        return TaskEntity.builder()
                .user(TASK_USER)
                .title(TASK_TITLE)
                .description(TASK_DESCRIPTION)
                .dateTimeWhenYouNeedToComplete(TASK_DATE_TIME_WHEN_YOU_NEED_TO_COMPLETE)
                .timeToCompleteInMinutes(TASK_TIME_TO_COMPLETE_IN_MINUTES)
                .importance(TASK_IMPORTANCE)
                .build();
    }

    public static TaskEntity buildDifferentTaskEntity() {
        return TaskEntity.builder()
                .id(DIFFERENT_TASK_ID)
                .user(TASK_USER)
                .title(DIFFERENT_TASK_TITLE)
                .description(DIFFERENT_TASK_DESCRIPTION)
                .dateTimeWhenYouNeedToComplete(DIFFERENT_TASK_DATE_TIME_WHEN_YOU_NEED_TO_COMPLETE)
                .timeToCompleteInMinutes(DIFFERENT_TASK_TIME_TO_COMPLETE_IN_MINUTES)
                .importance(DIFFERENT_TASK_IMPORTANCE)
                .status(DIFFERENT_TASK_STATUS)
                .build();
    }

    public static TaskEntity buildDifferentTaskEntityWithSameId() {
        return TaskEntity.builder()
                .id(TASK_ID)
                .user(TASK_USER)
                .title(DIFFERENT_TASK_TITLE)
                .description(DIFFERENT_TASK_DESCRIPTION)
                .dateTimeWhenYouNeedToComplete(DIFFERENT_TASK_DATE_TIME_WHEN_YOU_NEED_TO_COMPLETE)
                .timeToCompleteInMinutes(DIFFERENT_TASK_TIME_TO_COMPLETE_IN_MINUTES)
                .importance(DIFFERENT_TASK_IMPORTANCE)
                .status(DIFFERENT_TASK_STATUS)
                .build();
    }

    public static TaskEntity buildTaskEntityWithTitle(
            String title
    ) {
        return TaskEntity.builder()
                .id(TASK_ID)
                .user(TASK_USER)
                .title(title)
                .description(TASK_DESCRIPTION)
                .dateTimeWhenYouNeedToComplete(TASK_DATE_TIME_WHEN_YOU_NEED_TO_COMPLETE)
                .timeToCompleteInMinutes(TASK_TIME_TO_COMPLETE_IN_MINUTES)
                .importance(TASK_IMPORTANCE)
                .status(TASK_STATUS)
                .build();
    }

    public static TaskEntity buildTaskEntityWithDescription(
            String description
    ) {
        return TaskEntity.builder()
                .id(TASK_ID)
                .user(TASK_USER)
                .title(TASK_TITLE)
                .description(description)
                .dateTimeWhenYouNeedToComplete(TASK_DATE_TIME_WHEN_YOU_NEED_TO_COMPLETE)
                .timeToCompleteInMinutes(TASK_TIME_TO_COMPLETE_IN_MINUTES)
                .importance(TASK_IMPORTANCE)
                .status(TASK_STATUS)
                .build();
    }

    public static TaskEntity buildTaskEntityWithDateTimeWhenYouNeedToComplete(
            LocalDateTime dateTimeWhenYouNeedToComplete
    ) {
        return TaskEntity.builder()
                .id(TASK_ID)
                .user(TASK_USER)
                .title(TASK_TITLE)
                .description(TASK_DESCRIPTION)
                .dateTimeWhenYouNeedToComplete(dateTimeWhenYouNeedToComplete)
                .timeToCompleteInMinutes(TASK_TIME_TO_COMPLETE_IN_MINUTES)
                .importance(TASK_IMPORTANCE)
                .status(TASK_STATUS)
                .build();
    }

    public static TaskEntity buildTaskEntityWithTimeToCompleteInMinutes(
            Integer timeToCompleteInMinutes
    ) {
        return TaskEntity.builder()
                .id(TASK_ID)
                .user(TASK_USER)
                .title(TASK_TITLE)
                .description(TASK_DESCRIPTION)
                .dateTimeWhenYouNeedToComplete(TASK_DATE_TIME_WHEN_YOU_NEED_TO_COMPLETE)
                .timeToCompleteInMinutes(timeToCompleteInMinutes)
                .importance(TASK_IMPORTANCE)
                .status(TASK_STATUS)
                .build();
    }

    public static TaskEntity buildTaskEntityWithImportance(
            Importance importance
    ) {
        return TaskEntity.builder()
                .id(TASK_ID)
                .user(TASK_USER)
                .title(TASK_TITLE)
                .description(TASK_DESCRIPTION)
                .dateTimeWhenYouNeedToComplete(TASK_DATE_TIME_WHEN_YOU_NEED_TO_COMPLETE)
                .timeToCompleteInMinutes(TASK_TIME_TO_COMPLETE_IN_MINUTES)
                .importance(importance)
                .status(TASK_STATUS)
                .build();
    }

    public static TaskEntity buildTaskEntityWithStatus(
            Status status
    ) {
        return TaskEntity.builder()
                .id(TASK_ID)
                .user(TASK_USER)
                .title(TASK_TITLE)
                .description(TASK_DESCRIPTION)
                .dateTimeWhenYouNeedToComplete(TASK_DATE_TIME_WHEN_YOU_NEED_TO_COMPLETE)
                .timeToCompleteInMinutes(TASK_TIME_TO_COMPLETE_IN_MINUTES)
                .importance(TASK_IMPORTANCE)
                .status(status)
                .build();
    }
}
