package com.ufape.finproservice.dto;

import com.ufape.finproservice.enumeration.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTO {
    private String name;
    private String email;
    private String password;
    private Role role;
}
