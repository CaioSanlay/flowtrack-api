package com.caio.flowtrack_api.controller;


import com.caio.flowtrack_api.entity.Project;
import com.caio.flowtrack_api.service.ProjectService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/projects")
@AllArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    public Project create(@RequestBody Project project) {
        return projectService.create(project);
    }

    @GetMapping
    public List<Project> findAll() {
        return projectService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Project> findById(@PathVariable Long id) {
        return projectService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        projectService.delete(id);
    }
}
