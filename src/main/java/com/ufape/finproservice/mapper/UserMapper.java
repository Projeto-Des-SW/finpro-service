package com.ufape.finproservice.mapper;

import com.ufape.finproservice.dto.UserDTO;
import com.ufape.finproservice.model.User;

public class UserMapper {

    public static User toEntity(UserDTO dto) {
        if (dto == null) return null;
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        return user;
    }
}
