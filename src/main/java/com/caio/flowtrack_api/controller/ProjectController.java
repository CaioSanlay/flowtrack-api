package com.caio.flowtrack_api.controller;


import com.caio.flowtrack_api.dto.ProjectRequestDTO;
import com.caio.flowtrack_api.dto.ProjectResponseDTO;
import com.caio.flowtrack_api.entity.Project;
import com.caio.flowtrack_api.service.ProjectService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/projects")
@AllArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    public ResponseEntity<ProjectResponseDTO> create(@RequestBody @Valid ProjectRequestDTO projectRequestDTO) {
        Project project = new Project();
        project.setName(projectRequestDTO.getName());
        project.setDescription(projectRequestDTO.getDescription());

        Project createdProject = projectService.create(project);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToResponseDTO(createdProject));
}

    @GetMapping
    public ResponseEntity<List<ProjectResponseDTO>> findAll() {
        List<ProjectResponseDTO> response = projectService.findAll()
                .stream()
                .map(this:: convertToResponseDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponseDTO> findById(@PathVariable Long id) {
        Project project = projectService.findById(id);
        return ResponseEntity.ok(convertToResponseDTO(project));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        projectService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private ProjectResponseDTO convertToResponseDTO(Project project) {
        return new ProjectResponseDTO(
                project.getId(),
                project.getName(),
                project.getDescription()
        );
    }
}
