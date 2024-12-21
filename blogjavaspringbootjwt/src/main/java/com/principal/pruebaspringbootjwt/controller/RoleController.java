package com.principal.pruebaspringbootjwt.controller;

import com.principal.pruebaspringbootjwt.dto.RoleDTO;
import com.principal.pruebaspringbootjwt.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/getall")
    public ResponseEntity<List<RoleDTO>> getAllUsers() {
        List<RoleDTO> lstRoles = roleService.findAllRoles();
        return ResponseEntity.ok(lstRoles);
    }
}
