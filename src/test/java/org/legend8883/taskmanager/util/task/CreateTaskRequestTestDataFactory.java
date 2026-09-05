package org.legend8883.taskmanager.util.task;

import lombok.experimental.UtilityClass;
import org.legend8883.taskmanager.tasks.api.dto.requests.CreateTaskRequest;
import org.legend8883.taskmanager.tasks.db.enums.Importance;

import java.time.LocalDateTime;

import static org.legend8883.taskmanager.util.task.TaskTestFields.*;

@UtilityClass
public final class CreateTaskRequestTestDataFactory {
    public static CreateTaskRequest buildCreateTaskRequest() {
        return new CreateTaskRequest(
                TASK_TITLE,
                TASK_DESCRIPTION,
                TASK_DATE_TIME_WHEN_YOU_NEED_TO_COMPLETE,
                TASK_TIME_TO_COMPLETE_IN_MINUTES,
                TASK_IMPORTANCE
        );
    }

    public static CreateTaskRequest buildCreateTaskRequestWithTitle(String title) {
        return new CreateTaskRequest(
                title,
                TASK_DESCRIPTION,
                TASK_DATE_TIME_WHEN_YOU_NEED_TO_COMPLETE,
                TASK_TIME_TO_COMPLETE_IN_MINUTES,
                TASK_IMPORTANCE
        );
    }

    public static CreateTaskRequest buildCreateTaskRequestWithDescription(String description) {
        return new CreateTaskRequest(
                TASK_TITLE,
                description,
                TASK_DATE_TIME_WHEN_YOU_NEED_TO_COMPLETE,
                TASK_TIME_TO_COMPLETE_IN_MINUTES,
                TASK_IMPORTANCE
        );
    }

    public static CreateTaskRequest buildCreateTaskRequestWithDateTimeWhenYouNeedToComplete(LocalDateTime dateTimeWhenYouNeedToComplete) {
        return new CreateTaskRequest(
                TASK_TITLE,
                TASK_DESCRIPTION,
                dateTimeWhenYouNeedToComplete,
                TASK_TIME_TO_COMPLETE_IN_MINUTES,
                TASK_IMPORTANCE
        );
    }

    public static CreateTaskRequest buildCreateTaskRequestWithImportance(Importance importance) {
        return new CreateTaskRequest(
                TASK_TITLE,
                TASK_DESCRIPTION,
                TASK_DATE_TIME_WHEN_YOU_NEED_TO_COMPLETE,
                TASK_TIME_TO_COMPLETE_IN_MINUTES,
                importance
        );
    }
}
