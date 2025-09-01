package com.ufape.finproservice.service;

import com.ufape.finproservice.dto.LoginDTO;
import com.ufape.finproservice.dto.response.LoginResponseDTO;
import com.ufape.finproservice.dto.UserDTO;
import com.ufape.finproservice.exception.CustomException;
import com.ufape.finproservice.exception.ExceptionMessage;
import com.ufape.finproservice.mapper.UserMapper;
import com.ufape.finproservice.model.UserEntity;
import com.ufape.finproservice.repository.UserRepository;
import com.ufape.finproservice.security.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserEntity save(UserDTO userDTO) {
        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new CustomException(ExceptionMessage.EMAIL_ALREADY_EXISTS);
        }
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        UserEntity user = UserMapper.toEntity(userDTO);
        return userRepository.save(user);
    }

    public LoginResponseDTO authenticate(LoginDTO loginDTO) {
        UserEntity user = userRepository.findByEmail(loginDTO.getEmail())
                .orElseThrow(() -> new CustomException(ExceptionMessage.USER_NOT_FOUND));

        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new CustomException(ExceptionMessage.AUTHENTICATION_FAILED);
        }

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());
        return new LoginResponseDTO(token, UserMapper.toUserResponseDTO(user));
    }
}
