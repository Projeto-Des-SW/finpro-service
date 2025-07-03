package com.ufape.finproservice.mapper;

import com.ufape.finproservice.dto.UserDTO;
import com.ufape.finproservice.dto.UserResponseDTO;
import com.ufape.finproservice.model.User;

public class UserMapper {

    public static User toEntity(UserDTO dto) {
        if (dto == null) return null;
        return User.builder()
                .email(dto.getEmail())
                .name(dto.getName())
                .password(dto.getPassword())
                .role(dto.getRole())
                .build();
    }

    public static UserResponseDTO toUserResponseDTO(User user) {
        if (user == null) return null;
        return UserResponseDTO.builder()
                .email(user.getEmail())
                .name(user.getName())
                .role(user.getRole())
                .build();
    }
}
