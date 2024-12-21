package com.principal.pruebaspringbootjwt.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "tb_comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "comment_id")
    private UUID id;

    @Column(name = "comment_body")
    private String body;

    @Column(name = "comment_date", nullable = false, unique = true)
    private LocalDateTime publicationDate;

    @ManyToOne
    @JoinColumn(name = "comment_user")
    private User user;

    @ManyToOne
    @JoinColumn(name = "comment_post")
    private Post post;


}
