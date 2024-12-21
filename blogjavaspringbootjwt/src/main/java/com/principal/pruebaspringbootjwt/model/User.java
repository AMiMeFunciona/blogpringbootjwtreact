package com.principal.pruebaspringbootjwt.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "tb_users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id")
    private UUID id;

    @Column(name = "user_email", unique = true)
    @NotBlank
    @Size(min = 12, max = 25, message = "El email debe tener entre 12 y 25 caracteres")
    private String email;

    @Column(name = "user_password")
    private String password;

    @ManyToMany
    @JoinTable(name = "tb_users_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "rol_id"),
        uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id, rol_id"})}
    )
    private List<Role> roles;


    @OneToMany(mappedBy = "user")
    private List<Comment> comments;

    @OneToMany(mappedBy = "user")
    private List<Post> posts;

    @Column(name = "user_enabled")
    private boolean enabled;


}
