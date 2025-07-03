package com.ufape.finproservice.service;

import com.ufape.finproservice.dto.UserDTO;
import com.ufape.finproservice.mapper.UserMapper;
import com.ufape.finproservice.model.User;
import com.ufape.finproservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public User save(UserDTO userDTO) {
        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new RuntimeException("E-mail já está em uso");
        }
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        User user = UserMapper.toEntity(userDTO);
        return userRepository.save(user);
    }
}
