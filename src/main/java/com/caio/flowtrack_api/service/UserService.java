package com.caio.flowtrack_api.service;

import com.caio.flowtrack_api.entity.User;
import com.caio.flowtrack_api.exception.DuplicateResourceException;
import com.caio.flowtrack_api.exception.ResourceNotFoundException;
import com.caio.flowtrack_api.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User create(User user) {
        if(userRepository.existsByEmail(user.getEmail())){
            throw new DuplicateResourceException("Email already exists");
        }

        return userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found");
        }
        userRepository.deleteById(id);
    }
}
