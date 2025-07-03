package com.ufape.finproservice.controller;

import com.ufape.finproservice.dto.UserDTO;
import com.ufape.finproservice.model.User;
import com.ufape.finproservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    @Operation
    public ResponseEntity<User> createUser(@RequestBody UserDTO user) {
        User savedUser = userService.save(user);
        return ResponseEntity.ok(savedUser);
    }
}
