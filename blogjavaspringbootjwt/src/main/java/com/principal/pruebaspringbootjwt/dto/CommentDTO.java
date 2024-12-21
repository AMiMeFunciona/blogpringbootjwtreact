package com.principal.pruebaspringbootjwt.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {

    private UUID id;
    private String body;
    private LocalDateTime publicationDate;
    private UUID postId;
    private String userEmail;

}
