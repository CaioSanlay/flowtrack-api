package com.caio.flowtrack_api.service;

import com.caio.flowtrack_api.entity.Project;
import com.caio.flowtrack_api.entity.Task;
import com.caio.flowtrack_api.entity.User;
import com.caio.flowtrack_api.enums.TaskPriority;
import com.caio.flowtrack_api.enums.TaskStatus;
import com.caio.flowtrack_api.exception.BusinessRuleException;
import com.caio.flowtrack_api.exception.DuplicateResourceException;
import com.caio.flowtrack_api.exception.ResourceNotFoundException;
import com.caio.flowtrack_api.repository.ProjectRepository;
import com.caio.flowtrack_api.repository.TaskRepository;
import com.caio.flowtrack_api.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


@Service
@AllArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    public Task create(Task task) {
        Long userId = task.getUser().getId();
        Long projectId = task.getProject().getId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        if (taskRepository.existsByTitleAndUserId(task.getTitle(), userId)){
            throw new DuplicateResourceException("Task already exists for this user");
        }

        if (task.getDueDate() != null && task.getDueDate().isBefore(java.time.LocalDate.now())){
            throw new BusinessRuleException("Due date cannot be in the past");
        }

        task.setUser(user);
        task.setProject(project);

        if(task.getStatus() == null) {
            task.setStatus(TaskStatus.PENDING);
        }

        return taskRepository.save(task);
    }

    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    public Task findById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));
    }

    public List<Task> findByStatus(TaskStatus status) {
        return taskRepository.findByStatus(status);
    }

    public List<Task> findByPriority(TaskPriority priority) {
        return taskRepository.findByPriority(priority);
    }

    public Task update(Long id, Task updatedTask) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));

        Long userId = task.getUser().getId();

        if (updatedTask.getUser() != null && updatedTask.getUser().getId() != null) {
            User user = userRepository.findById(updatedTask.getUser().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));
            task.setUser(user);
            userId = user.getId();
        }

        if (updatedTask.getProject() != null && updatedTask.getProject().getId() != null) {
            Project project = projectRepository.findById(updatedTask.getProject().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Project not found"));
            task.setProject(project);
        }

        if (updatedTask.getTitle() != null &&
                taskRepository.existsByTitleAndUserIdAndIdNot(updatedTask.getTitle(), userId, id)) {
            throw new DuplicateResourceException("Task already exists for this user");
        }

        if ( updatedTask.getDueDate() != null && updatedTask.getDueDate().isBefore(LocalDate.now())) {
            throw new BusinessRuleException("Due date cannot be in the past");
        }

        task.setTitle((updatedTask.getTitle()));
        task.setDescription(updatedTask.getDescription());
        task.setDueDate(updatedTask.getDueDate());
        task.setStatus(updatedTask.getStatus());
        task.setPriority(updatedTask.getPriority());

        return taskRepository.save(task);

    }

    public void delete(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new ResourceNotFoundException("Task not found");
        }
        taskRepository.deleteById(id);
    }

}
