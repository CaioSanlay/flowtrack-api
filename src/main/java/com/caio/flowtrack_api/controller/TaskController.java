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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/tasks")
@AllArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<Task> create(@RequestBody @Valid TaskRequestDTO taskRequestDTO) {
        Task task = convertToEntity(taskRequestDTO);
        Task createdTask = taskService.create(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    }

    @GetMapping
    public ResponseEntity<List<Task>> findAll() {
        return ResponseEntity.ok(taskService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> findById(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.findById(id));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Task>> findByStatus(@PathVariable TaskStatus status) {
        return ResponseEntity.ok(taskService.findByStatus(status));
    }

    @GetMapping("/priority/{priority}")
    public ResponseEntity<List<Task>> findByPriority(@PathVariable TaskPriority priority){
        return ResponseEntity.ok(taskService.findByPriority(priority));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> update(@PathVariable Long id, @RequestBody @Valid TaskRequestDTO taskRequestDTO) {
        Task task = convertToEntity(taskRequestDTO);
        Task updatedTask = taskService.update(id, task);
        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        taskService.delete(id);
        return ResponseEntity.noContent().build();
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
