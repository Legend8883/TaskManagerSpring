package util.task;

import lombok.experimental.UtilityClass;
import org.legend8883.taskmanager.tasks.api.dto.requests.CreateTaskRequest;

import static util.task.TaskTestFields.*;

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
}
