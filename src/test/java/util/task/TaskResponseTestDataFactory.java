package util.task;

import lombok.experimental.UtilityClass;
import org.legend8883.taskmanager.tasks.api.dto.responses.TaskResponse;
import org.legend8883.taskmanager.tasks.db.enums.Status;
import util.user.UserTestDataFactory;

import static util.task.TaskTestFields.*;

@UtilityClass
public final class TaskResponseTestDataFactory {
    public static TaskResponse buildTaskResponse() {
        return new TaskResponse(
                TASK_ID,
                UserTestDataFactory.buildSimpleUserResponse(),
                TASK_TITLE,
                TASK_DESCRIPTION,
                TASK_DATE_TIME_WHEN_YOU_NEED_TO_COMPLETE,
                TASK_TIME_TO_COMPLETE_IN_MINUTES,
                TASK_IMPORTANCE,
                TASK_STATUS,
                TASK_CREATED_AT,
                TASK_UPDATED_AT
        );
    }

    public static TaskResponse buildDifferentTaskResponse() {
        return new TaskResponse(
                DIFFERENT_TASK_ID,
                UserTestDataFactory.buildSimpleUserResponse(),
                DIFFERENT_TASK_TITLE,
                DIFFERENT_TASK_DESCRIPTION,
                DIFFERENT_TASK_DATE_TIME_WHEN_YOU_NEED_TO_COMPLETE,
                DIFFERENT_TASK_TIME_TO_COMPLETE_IN_MINUTES,
                DIFFERENT_TASK_IMPORTANCE,
                DIFFERENT_TASK_STATUS,
                TASK_CREATED_AT,
                TASK_UPDATED_AT
        );
    }

    public static TaskResponse buildTaskResponseWithStatus(Status status) {
        return new TaskResponse(
                TASK_ID,
                UserTestDataFactory.buildSimpleUserResponse(),
                TASK_TITLE,
                TASK_DESCRIPTION,
                TASK_DATE_TIME_WHEN_YOU_NEED_TO_COMPLETE,
                TASK_TIME_TO_COMPLETE_IN_MINUTES,
                TASK_IMPORTANCE,
                status,
                TASK_CREATED_AT,
                TASK_UPDATED_AT
        );
    }
}
