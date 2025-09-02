package com.ufape.finproservice.controller;

import com.ufape.finproservice.dto.LoginDTO;
import com.ufape.finproservice.dto.response.LoginResponseDTO;
import com.ufape.finproservice.dto.UserDTO;
import com.ufape.finproservice.model.UserEntity;
import com.ufape.finproservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/create")
    @Operation
    public ResponseEntity<UserEntity> createUser(@RequestBody UserDTO userDTO) {
        UserEntity savedUser = userService.save(userDTO);
        return ResponseEntity.ok(savedUser);
    }

    @PostMapping("/login")
    @Operation
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        LoginResponseDTO user = userService.authenticate(loginDTO);
        return ResponseEntity.ok(user);
    }
}
