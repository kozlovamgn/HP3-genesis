package com.example.genesis.service;

import com.example.genesis.model.UserEntity;
import com.example.genesis.storage.UserRepository;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public final class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Long createUser(UserEntity user) throws DuplicatePersonIdException {
        try {
            userRepository.save(user);
        } catch (DuplicateKeyException e) {
            throw new DuplicatePersonIdException("Person with person ID " + user.personId() + " already exists", e);
        }

        return user.id();
    }

    public UserEntity findUserById(Long userId) {
        return userRepository.findById(userId);
    }

    public List<UserEntity> findAllUsers() {
        return userRepository.findAll();
    }

    public void updateUser(Long userId, String name, String surname) throws UserNotFoundException {
        if (userRepository.updateUser(userId, name, surname) == 0)
            throw new UserNotFoundException("User with ID " + userId + " not found");
    }
}
