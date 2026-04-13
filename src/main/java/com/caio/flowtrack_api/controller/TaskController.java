package com.caio.flowtrack_api.controller;


import com.caio.flowtrack_api.dto.TaskProjectRequestDTO;
import com.caio.flowtrack_api.dto.TaskRequestDTO;
import com.caio.flowtrack_api.dto.TaskResponseDTO;
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
import java.util.stream.Collectors;


@RestController
@RequestMapping("/tasks")
@AllArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<TaskResponseDTO> create(@RequestBody @Valid TaskRequestDTO taskRequestDTO) {
        Task task = convertToEntity(taskRequestDTO);
        Task createdTask = taskService.create(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToResponseDTO(createdTask));
    }

    @GetMapping
    public ResponseEntity<List<TaskResponseDTO>> findAll() {
        List<TaskResponseDTO> response = taskService.findAll()
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> findById(@PathVariable Long id) {
        Task task = taskService.findById(id);
        return ResponseEntity.ok(convertToResponseDTO(task));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<TaskResponseDTO>> findByStatus(@PathVariable TaskStatus status) {
        List<TaskResponseDTO> response = taskService.findByStatus(status)
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/priority/{priority}")
    public ResponseEntity<List<TaskResponseDTO>> findByPriority(@PathVariable TaskPriority priority){
        List<TaskResponseDTO> response = taskService.findByPriority(priority)
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> update(@PathVariable Long id, @RequestBody @Valid TaskRequestDTO taskRequestDTO) {
        Task task = convertToEntity(taskRequestDTO);
        Task updatedTask = taskService.update(id, task);
        return ResponseEntity.ok(convertToResponseDTO(updatedTask));
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

    private TaskResponseDTO convertToResponseDTO(Task task) {
        return new TaskResponseDTO(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getDueDate(),
                task.getStatus(),
                task.getPriority(),
                task.getUser().getId(),
                task.getUser().getName(),
                task.getProject().getId(),
                task.getProject().getName()
        );
    }
}
