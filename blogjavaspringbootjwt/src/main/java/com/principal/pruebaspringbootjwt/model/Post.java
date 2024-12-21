package com.principal.pruebaspringbootjwt.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "tb_posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "post_id")
    private UUID id;

    @Column(name = "post_title")
    private String title;

    @Column(name = "post_body")
    private String body;

    @Column(name = "post_date", nullable = false, unique = true)
    private LocalDateTime publicationDate;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments;

    @ManyToOne
    @JoinColumn(name = "post_user")
    private User user;


}
