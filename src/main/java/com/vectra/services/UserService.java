package com.vectra.services;

import com.vectra.entities.UserEntity;
import com.vectra.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity createUser(UserEntity userEntity) {
        // Add business logic to create a user
        return userRepository.save(userEntity);
    }

    public UserEntity updateUser(UserEntity userEntity) {
        // Add business logic to update a user
        return userRepository.save(userEntity);
    }

    public void deleteUser(Long id) {
        // Add business logic to delete a user
        userRepository.deleteById(id);
    }

    public UserEntity findUserByEmail(String email) {
        // Add business logic to find a user by their email
        return userRepository.findByEmail(email);
    }
}