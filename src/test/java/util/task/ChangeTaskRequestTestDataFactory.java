package util.task;

import lombok.experimental.UtilityClass;
import org.legend8883.taskmanager.tasks.api.dto.requests.ChangeTaskRequest;
import org.legend8883.taskmanager.tasks.db.enums.Importance;
import org.legend8883.taskmanager.tasks.db.enums.Status;

import java.time.LocalDateTime;

import static util.task.TaskTestFields.*;

@UtilityClass
public final class ChangeTaskRequestTestDataFactory {
    public static ChangeTaskRequest buildChangeTaskRequest() {
        return new ChangeTaskRequest(
                TASK_TITLE,
                TASK_DESCRIPTION,
                TASK_DATE_TIME_WHEN_YOU_NEED_TO_COMPLETE,
                TASK_TIME_TO_COMPLETE_IN_MINUTES,
                TASK_IMPORTANCE,
                TASK_STATUS
        );
    }

    public static ChangeTaskRequest buildDifferentChangeTaskRequest() {
        return new ChangeTaskRequest(
                DIFFERENT_TASK_TITLE,
                DIFFERENT_TASK_DESCRIPTION,
                DIFFERENT_TASK_DATE_TIME_WHEN_YOU_NEED_TO_COMPLETE,
                DIFFERENT_TASK_TIME_TO_COMPLETE_IN_MINUTES,
                DIFFERENT_TASK_IMPORTANCE,
                DIFFERENT_TASK_STATUS
        );
    }

    public static ChangeTaskRequest buildChangeTaskRequestWithNullFieldsAndTitle(
            String title
    ) {
        return new ChangeTaskRequest(
                title,
                null,
                null,
                null,
                null,
                null
        );
    }

    public static ChangeTaskRequest buildChangeTaskRequestWithNullFieldsAndDescription(
            String description
    ) {
        return new ChangeTaskRequest(
                null,
                description,
                null,
                null,
                null,
                null
        );
    }

    public static ChangeTaskRequest buildChangeTaskRequestWithNullFieldsAndDateTimeWhenYouNeedToComplete(
            LocalDateTime dateTimeWhenYouNeedToComplete
    ) {
        return new ChangeTaskRequest(
                null,
                null,
                dateTimeWhenYouNeedToComplete,
                null,
                null,
                null
        );
    }

    public static ChangeTaskRequest buildChangeTaskRequestWithNullFieldsAndTimeToCompleteInMinutes(
            Integer timeToCompleteInMinutes
    ) {
        return new ChangeTaskRequest(
                null,
                null,
                null,
                timeToCompleteInMinutes,
                null,
                null
        );
    }

    public static ChangeTaskRequest buildChangeTaskRequestWithNullFieldsAndImportance(
            Importance importance
    ) {
        return new ChangeTaskRequest(
                null,
                null,
                null,
                null,
                importance,
                null
        );
    }

    public static ChangeTaskRequest buildChangeTaskRequestWithNullFieldsAndStatus(
            Status status
    ) {
        return new ChangeTaskRequest(
                null,
                null,
                null,
                null,
                null,
                status
        );
    }
}
