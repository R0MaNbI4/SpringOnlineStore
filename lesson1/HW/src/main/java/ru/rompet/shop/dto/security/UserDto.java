package ru.rompet.shop.dto.security;

import lombok.Data;

@Data
public class UserDto {
    Long id;
    String name;
    String password;
    String email;
    Boolean enabled;
    String authorities;
}
