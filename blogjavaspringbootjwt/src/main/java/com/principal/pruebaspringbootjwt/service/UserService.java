package com.principal.pruebaspringbootjwt.service;

import com.principal.pruebaspringbootjwt.dto.AuthUserDto;
import com.principal.pruebaspringbootjwt.dto.UserDTO;
import com.principal.pruebaspringbootjwt.model.Role;
import com.principal.pruebaspringbootjwt.model.User;
import com.principal.pruebaspringbootjwt.repository.RoleRepository;
import com.principal.pruebaspringbootjwt.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserService(RoleRepository roleRepository, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Transactional
    public List<UserDTO> getAllUser() {
        return userRepository.findAll()
                .stream()
                .map(user -> new ModelMapper().map(user, UserDTO.class))
                .collect(Collectors.toList());
    }

    public User registerUser(AuthUserDto authUserDto) {
        User user = new User();
        user.setEmail(authUserDto.getEmail());
        user.setPassword(passwordEncoder.encode(authUserDto.getPassword()));
        user.setEnabled(authUserDto.isEnabled());
        List<Role> roles = roleRepository.findAllById(authUserDto.getRoleIds());
        user.setRoles(roles);
        return userRepository.save(user);
    }
}
