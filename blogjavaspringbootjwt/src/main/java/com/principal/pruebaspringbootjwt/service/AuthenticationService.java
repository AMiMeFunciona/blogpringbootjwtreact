package com.principal.pruebaspringbootjwt.service;

import com.principal.pruebaspringbootjwt.dto.AuthUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    public UserDetails authenticate(AuthUserDto authUserDto) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authUserDto.getEmail(),
                        authUserDto.getPassword()
                )
        );
        return userDetailsService.loadUserByUsername(authUserDto.getEmail());

    }

}
