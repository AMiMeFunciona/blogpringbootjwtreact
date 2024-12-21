package com.principal.pruebaspringbootjwt.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuthUserDto {

    private String email;
    private String password;
    private List<UUID> roleIds;
    private boolean enabled;

}
