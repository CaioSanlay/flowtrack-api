package com.caio.flowtrack_api.repository;

import com.caio.flowtrack_api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
