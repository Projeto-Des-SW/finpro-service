package com.ufape.finproservice.mapper;

import com.ufape.finproservice.dto.UserDTO;
import com.ufape.finproservice.dto.response.UserResponseDTO;
import com.ufape.finproservice.model.UserEntity;

public class UserMapper {

    public static UserEntity toEntity(UserDTO dto) {
        if (dto == null) return null;
        return UserEntity.builder()
                .email(dto.getEmail())
                .name(dto.getName())
                .password(dto.getPassword())
                .role(dto.getRole())
                .build();
    }

    public static UserResponseDTO toUserResponseDTO(UserEntity user) {
        if (user == null) return null;
        return UserResponseDTO.builder()
                .email(user.getEmail())
                .name(user.getName())
                .role(user.getRole())
                .build();
    }
}
