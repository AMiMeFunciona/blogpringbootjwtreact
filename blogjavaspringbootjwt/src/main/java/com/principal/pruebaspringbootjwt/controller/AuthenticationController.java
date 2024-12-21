package com.principal.pruebaspringbootjwt.controller;

import com.principal.pruebaspringbootjwt.dto.AuthUserDto;
import com.principal.pruebaspringbootjwt.dto.UserDTO;
import com.principal.pruebaspringbootjwt.service.AuthenticationService;
import com.principal.pruebaspringbootjwt.service.JwtService;
import com.principal.pruebaspringbootjwt.service.RoleService;
import com.principal.pruebaspringbootjwt.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private JwtService jwtService;


    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody AuthUserDto authUserDto) {
        UserDTO authuserDTO = new ModelMapper().map(userService.registerUser(authUserDto), UserDTO.class);
        return ResponseEntity.ok(authuserDTO);
    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout() {
        return ResponseEntity.ok(Collections.singletonMap("message", "Logout exitoso"));
    }

    @PostMapping("/authlogin")
    public ResponseEntity<Map<String, String>> authlogin(@RequestBody AuthUserDto authUserDto) {

        try {
            UserDetails authenticatedUser = authenticationService.authenticate(authUserDto);
            List<String> roles = authenticatedUser.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());
            final String jwt = jwtService.generateToken(authenticatedUser, roles);
            return ResponseEntity.ok(Collections.singletonMap("token", jwt));
        } catch (
            AuthenticationException e) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Collections.singletonMap("error", "Credenciales incorrectas"));
        }
    }

}
