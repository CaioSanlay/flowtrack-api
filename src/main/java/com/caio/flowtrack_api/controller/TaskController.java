package com.caio.flowtrack_api.controller;


import com.caio.flowtrack_api.dto.TaskProjectRequestDTO;
import com.caio.flowtrack_api.dto.TaskRequestDTO;
import com.caio.flowtrack_api.dto.TaskUserRequestDTO;
import com.caio.flowtrack_api.entity.Project;
import com.caio.flowtrack_api.entity.Task;
import com.caio.flowtrack_api.entity.User;
import com.caio.flowtrack_api.enums.TaskPriority;
import com.caio.flowtrack_api.enums.TaskStatus;
import com.caio.flowtrack_api.service.TaskService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tasks")
@AllArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public Task create(@RequestBody @Valid TaskRequestDTO taskRequestDTO) {
        Task task = convertToEntity(taskRequestDTO);
        return taskService.create(task);
    }

    @GetMapping
    public List<Task> findAll() {
        return taskService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Task> findById(@PathVariable Long id) {
        return taskService.findById(id);
    }

    @GetMapping("/status/{status}")
    public List<Task> findByStatus(@PathVariable TaskStatus status) {
        return taskService.findByStatus(status);
    }

    @GetMapping("/priority/{priority}")
    public List<Task> findByPriority(@PathVariable TaskPriority priority){
        return taskService.findByPriority(priority);
    }

    @PutMapping("/{id}")
    public Task update(@PathVariable Long id, @RequestBody @Valid TaskRequestDTO taskRequestDTO) {
        Task task = convertToEntity(taskRequestDTO);
        return taskService.update(id,task);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        taskService.delete(id);
    }

    private Task convertToEntity(TaskRequestDTO dto) {
        Task task = new Task();
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setDueDate(dto.getDueDate());
        task.setStatus(dto.getStatus());
        task.setPriority(dto.getPriority());

        TaskUserRequestDTO userDTO = dto.getUser();
        User user = new User();
        user.setId(userDTO.getId());
        task.setUser(user);

        TaskProjectRequestDTO projectDTO = dto.getProject();
        Project project = new Project();
        project.setId(projectDTO.getId());
        task.setProject(project);

        return task;
    }
}
