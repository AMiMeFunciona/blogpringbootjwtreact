package com.principal.pruebaspringbootjwt.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {

    public PostDTO(UUID id, String title, String body, LocalDateTime publicationDate, String userEmail, int commentCount) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.publicationDate = publicationDate;
        this.userEmail = userEmail;
        this.commentCount = commentCount;
    }

    private UUID id;
    private String title;
    private String body;
    private LocalDateTime publicationDate;
    private List<CommentDTO> comments;
    private String userEmail;
    private int commentCount;

}
