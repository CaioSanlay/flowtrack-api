package com.caio.flowtrack_api.repository;

import com.caio.flowtrack_api.entity.Task;
import com.caio.flowtrack_api.enums.TaskPriority;
import com.caio.flowtrack_api.enums.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByStatus(TaskStatus status);

    List<Task> findByPriority(TaskPriority priority);
}
