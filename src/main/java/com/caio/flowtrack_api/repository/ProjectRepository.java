package com.caio.flowtrack_api.repository;

import com.caio.flowtrack_api.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
