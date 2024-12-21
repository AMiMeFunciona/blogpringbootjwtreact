package com.principal.pruebaspringbootjwt.repository;


import com.principal.pruebaspringbootjwt.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Repository
public interface CommentRepository extends JpaRepository<Comment, UUID> {

    Collection<Comment> findByPostId(UUID postId);

    @Modifying
    @Query("UPDATE Comment c SET c.body = :body WHERE c.id = :id")
    int updateComment(@Param("id") UUID id, @Param("body") String body);

    @Query("SELECT c FROM Comment c WHERE c.post.id = :postId ORDER BY c.publicationDate DESC")
    Page<Comment> findCommentsByPostId(@Param("postId") UUID postId, Pageable pageable);
}
