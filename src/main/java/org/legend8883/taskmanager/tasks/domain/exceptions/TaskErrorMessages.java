package org.legend8883.taskmanager.tasks.domain.exceptions;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class TaskErrorMessages {
    private static final String TASK_NOT_FOUND = "Task with id %d not found";

    public static String taskNotFound(Long id) {
        return String.format(TASK_NOT_FOUND, id);
    }

}
