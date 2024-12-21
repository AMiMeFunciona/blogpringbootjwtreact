package com.principal.pruebaspringbootjwt.repository;


import com.principal.pruebaspringbootjwt.dto.PostDTO;
import com.principal.pruebaspringbootjwt.model.Post;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {


    @Query("SELECT p FROM Post p WHERE p.id = :id")
    Optional<Post> findPostById(@Param("id") UUID id);

    List<Post> findByTitleContainingIgnoreCase(String title);

    @Query(value = "SELECT p.* FROM tb_posts p WHERE p.post_title LIKE %:title%", nativeQuery = true)
    List<Post> findByTitleNativeQuery(@Param("title") String title);

//    @Query("SELECT p FROM Post p " +
//            "JOIN FETCH p.user u " +
//            "LEFT JOIN FETCH p.comments c " +
//            "WHERE p.title LIKE %:title%")
//    List<Post> findByTitleFetching(@Param("title") String title);

    @Query("SELECT new com.principal.pruebaspringbootjwt.dto.PostDTO(" +
            "p.id, p.title, p.body, p.publicationDate, u.email, SIZE(p.comments)) " +
            "FROM Post p " +
            "JOIN p.user u " +
            "LEFT JOIN p.comments c " +
            "WHERE p.title LIKE %:title%")
    List<PostDTO> findByTitleFetching(@Param("title") String title);


    @Query("SELECT p FROM Post p ORDER BY p.publicationDate DESC")
    List<Post> findAllPostsOrderedByPublicationDateDesc();

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO tb_posts (post_id, post_title, post_body, post_date, post_user) VALUES (:postId, :title, :body, :publicationDate, :userId)", nativeQuery = true)
    void insertPost(@Param("postId") UUID postId,
                    @Param("title") String title,
                    @Param("body") String body,
                    @Param("publicationDate") LocalDateTime publicationDate,
                    @Param("userId") UUID userId);

    @Modifying
    @Query("DELETE FROM Post p WHERE p.id = :id")
    int deletePostById(@Param("id") UUID id);

    @Modifying
    @Query("UPDATE Post p SET p.title = :title, p.body = :body WHERE p.id = :id")
    int updatePost(@Param("id") UUID id, @Param("title") String title, @Param("body") String body);
}
