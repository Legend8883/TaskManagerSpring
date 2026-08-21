package org.legend8883.taskmanager.tasks.domain.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.legend8883.taskmanager.tasks.api.dto.requests.ChangeTaskRequest;
import org.legend8883.taskmanager.tasks.db.entities.TaskEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ChangeTaskUtil {
    public TaskEntity getChangedTaskEntity(
            TaskEntity taskEntity,
            ChangeTaskRequest request
    ) {
        if (request.title() != null) taskEntity.setTitle(request.title());
        if (request.description() != null) taskEntity.setDescription(request.description());
        if (request.dateTimeWhenYouNeedToComplete() != null)
            taskEntity.setDateTimeWhenYouNeedToComplete(request.dateTimeWhenYouNeedToComplete());
        if (request.timeToCompleteInMinutes() != null) taskEntity.setTimeToCompleteInMinutes(request.timeToCompleteInMinutes());
        if (request.importance() != null) taskEntity.setImportance(request.importance());
        if (request.status() != null) taskEntity.setStatus(request.status());

        return taskEntity;
    }
}
