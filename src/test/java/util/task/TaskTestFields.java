package util.task;

import lombok.experimental.UtilityClass;
import org.legend8883.taskmanager.tasks.db.enums.Importance;
import org.legend8883.taskmanager.tasks.db.enums.Status;
import org.legend8883.taskmanager.users.db.entities.UserEntity;
import util.user.UserTestDataFactory;

import java.time.LocalDateTime;

@UtilityClass
public final class TaskTestFields {
    public static final UserEntity TASK_USER = UserTestDataFactory.buildUserEntity();
    public static final Long TASK_ID = 67L;
    public static final String TASK_TITLE = "Learn coding";
    public static final String TASK_DESCRIPTION = "Become a pro coder";
    public static final LocalDateTime TASK_DATE_TIME_WHEN_YOU_NEED_TO_COMPLETE = LocalDateTime.of(
            2026,
            8,
            18,
            16,
            0);
    public static final Integer TASK_TIME_TO_COMPLETE_IN_MINUTES = 42;
    public static final Importance TASK_IMPORTANCE = Importance.HIGH;
    public static final Status TASK_STATUS = Status.PLANNED;
    public static final LocalDateTime TASK_CREATED_AT = LocalDateTime.of(
            2026,
            8,
            15,
            20,
            0);
    public static final LocalDateTime TASK_UPDATED_AT = LocalDateTime.of(
            2026,
            8,
            15,
            20,
            0);

    public static final Long DIFFERENT_TASK_ID = 676L;
    public static final String DIFFERENT_TASK_TITLE = "DifTestTitle";
    public static final String DIFFERENT_TASK_DESCRIPTION = "DifTestDescription";
    public static final LocalDateTime DIFFERENT_TASK_DATE_TIME_WHEN_YOU_NEED_TO_COMPLETE = LocalDateTime.of(
            2026,
            8, 20,
            10,
            0);
    public static final Integer DIFFERENT_TASK_TIME_TO_COMPLETE_IN_MINUTES = 52;
    public static final Importance DIFFERENT_TASK_IMPORTANCE = Importance.MEDIUM;
    public static final Status DIFFERENT_TASK_STATUS = Status.RUNNING;
}
