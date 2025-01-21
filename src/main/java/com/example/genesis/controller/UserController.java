package com.example.genesis.controller;

import com.example.genesis.controller.model.CreateUserRequest;
import com.example.genesis.controller.model.GetUserDetailResponse;
import com.example.genesis.controller.model.GetUserResponse;
import com.example.genesis.controller.model.UpdateUserRequest;
import com.example.genesis.model.UserEntity;
import com.example.genesis.service.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public final class UserController {
    private final UserService userService;
    private final PersonIdValidator personIdValidator;

    @Autowired
    public UserController(UserService userService, PersonIdValidator personIdValidator) {
        this.userService = userService;
        this.personIdValidator = personIdValidator;
    }


    @PostMapping("/api/v1/users")
    public ResponseEntity<Long> createUser(@RequestBody @Valid CreateUserRequest createUserRequest) {
        try {
            personIdValidator.validateId(createUserRequest.personId());

            Long userId = userService.createUser(new UserEntity(
                    null,
                    createUserRequest.name(),
                    createUserRequest.surname(),
                    createUserRequest.personId(),
                    UUID.randomUUID()
            ));

            return new ResponseEntity<>(userId, HttpStatus.CREATED);
        } catch (DuplicatePersonIdException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Person with this person ID already exists");
        } catch (InvalidPersonIdException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid person ID");
        }
    }

    @GetMapping("/api/v1/users")
    public ResponseEntity<List<GetUserResponse>> getAllUsers() {
        List<UserEntity> allUsers = userService.findAllUsers();
        List<GetUserResponse> userResponses = new ArrayList<>();

        for (UserEntity user : allUsers) {
            userResponses.add(new GetUserResponse(
                    user.id(),
                    user.name(),
                    user.surname()
            ));
        }

        return ResponseEntity.ok(userResponses);
    }

    @GetMapping(value = "/api/v1/users", params = "detail=true")
    public ResponseEntity<List<GetUserDetailResponse>> getAllUserDetails(@RequestParam(value = "detail", required = false) Boolean ignoredDetail) {
        List<UserEntity> allUsers = userService.findAllUsers();
        List<GetUserDetailResponse> userResponses = new ArrayList<>();

        for (UserEntity user : allUsers) {
            userResponses.add(new GetUserDetailResponse(
                    user.id(),
                    user.name(),
                    user.surname(),
                    user.personId(),
                    user.uuid()
            ));
        }

        return ResponseEntity.ok(userResponses);
    }

    @GetMapping("/api/v1/users/{id}")
    public ResponseEntity<GetUserResponse> getUserById(@PathVariable("id") Long id) {
        UserEntity user = userService.findUserById(id);

        if (user != null) {
            return ResponseEntity.ok(new GetUserResponse(
                    user.id(),
                    user.name(),
                    user.surname()
            ));
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
    }

    @GetMapping(value = "/api/v1/users/{id}", params = "detail=true")
    public ResponseEntity<GetUserDetailResponse> getUserDetailById(@PathVariable Long id, @RequestParam(value = "detail", required = false) Boolean ignoredDetail) {
        UserEntity user = userService.findUserById(id);

        if (user != null) {
            return ResponseEntity.ok(new GetUserDetailResponse(
                    user.id(),
                    user.name(),
                    user.surname(),
                    user.personId(),
                    user.uuid()
            ));
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
    }

    @PutMapping("/api/v1/users")
    public ResponseEntity<Void> updateUser(@RequestBody @Valid UpdateUserRequest updateUserRequest) {
        try {
            userService.updateUser(
                    updateUserRequest.id(),
                    updateUserRequest.name(),
                    updateUserRequest.surname()
            );

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
    }
}
