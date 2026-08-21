package org.legend8883.taskmanager.tasks.domain.services.managers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.legend8883.taskmanager.misc.PageableCreator;
import org.legend8883.taskmanager.tasks.api.dto.responses.TaskResponse;
import org.legend8883.taskmanager.tasks.db.entities.TaskEntity;
import org.legend8883.taskmanager.tasks.db.repositories.TaskRepository;
import org.legend8883.taskmanager.tasks.domain.mappers.TaskMapper;
import org.legend8883.taskmanager.users.db.entities.UserEntity;
import org.legend8883.taskmanager.users.domain.util.UserUtil;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class GetAllUserTasksManager {
    private final UserUtil userUtil;
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public List<TaskResponse> get(
            Integer pageSize,
            Integer pageNum
    ) {
        UserEntity currentUser = userUtil.getCurrentUser();

        Pageable pageable = PageableCreator.assemble(pageSize, pageNum);

        List<TaskEntity> userTasks = taskRepository.findAllByUser(currentUser, pageable);

        log.info("User tasks loaded");
        return taskMapper.entitiesToResponses(userTasks);
    }
}
