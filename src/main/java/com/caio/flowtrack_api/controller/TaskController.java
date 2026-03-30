package com.caio.flowtrack_api.controller;


import com.caio.flowtrack_api.entity.Task;
import com.caio.flowtrack_api.enums.TaskPriority;
import com.caio.flowtrack_api.enums.TaskStatus;
import com.caio.flowtrack_api.service.TaskService;
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
    public Task create(@RequestBody Task task) {
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
    public Task update(@PathVariable Long id, @RequestBody Task task) {
        return taskService.update(id,task);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        taskService.delete(id);
    }
}
