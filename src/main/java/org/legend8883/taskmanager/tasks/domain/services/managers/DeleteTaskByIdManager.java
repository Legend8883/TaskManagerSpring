package org.legend8883.taskmanager.tasks.domain.services.managers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.legend8883.taskmanager.tasks.db.repositories.TaskRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DeleteTaskByIdManager {
    private final TaskRepository taskRepository;

    public void delete(Long id) {
        taskRepository.deleteById(id);
        log.info("Deleted task with id {}", id);
    }
}
