package org.legend8883.taskmanager.tasks.domain.mappers;

import org.legend8883.taskmanager.tasks.api.dto.responses.TaskResponse;
import org.legend8883.taskmanager.tasks.db.entities.TaskEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    TaskResponse entityToResponse(TaskEntity taskEntity);

    List<TaskResponse> entitiesToResponses(List<TaskEntity> taskEntities);
}
