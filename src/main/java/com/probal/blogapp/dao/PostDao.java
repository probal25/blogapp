package com.probal.blogapp.dao;

import com.probal.blogapp.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostDao extends JpaRepository<Post, Long> {

    @Query(value = " select * from post p where p.user_id= ?1", nativeQuery = true)
    List<Post> allPostByUserId(long userId);


    @Query(value = " select * from post p where p.is_approved = 1", nativeQuery = true)
    List<Post> findAllApprovedPosts();

    @Query(value = " select * from post p where p.is_approved = 0", nativeQuery = true)
    List<Post> findAllNonApprovedPosts();
}
