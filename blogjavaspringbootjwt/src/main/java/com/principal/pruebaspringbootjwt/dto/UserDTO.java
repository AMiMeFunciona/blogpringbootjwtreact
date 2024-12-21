package com.principal.pruebaspringbootjwt.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private UUID id;
    private String email;
    private List<RoleDTO> roles;
    private boolean enabled;
}
