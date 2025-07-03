package com.ufape.finproservice.mapper;

import com.ufape.finproservice.dto.UserDTO;
import com.ufape.finproservice.model.User;

public class UserMapper {

    public static User toEntity(UserDTO dto) {
        if (dto == null) return null;
        return User.builder()
                .email(dto.getEmail())
                .name(dto.getName())
                .password(dto.getPassword())
                .build();
    }
}
