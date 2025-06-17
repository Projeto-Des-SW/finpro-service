package com.ufape.finproservice.service;

import com.ufape.finproservice.dto.UserDTO;
import com.ufape.finproservice.mapper.UserMapper;
import com.ufape.finproservice.model.User;
import com.ufape.finproservice.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User save(UserDTO userDTO) {
        User user = UserMapper.toEntity(userDTO);
        return userRepository.save(user);
    }
}

