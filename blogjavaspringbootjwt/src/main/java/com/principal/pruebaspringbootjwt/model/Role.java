package com.principal.pruebaspringbootjwt.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "tb_roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "rol_id")
    private UUID id;

    @Column(name = "rol_name", unique = true)
    private String name;

    @ManyToMany(mappedBy = "roles")
    private List<User> users;

}
