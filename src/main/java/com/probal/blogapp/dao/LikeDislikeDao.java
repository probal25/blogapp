package com.probal.blogapp.dao;

import com.probal.blogapp.model.LikeDislike;
import com.probal.blogapp.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface LikeDislikeDao extends JpaRepository<LikeDislike,Long> {


    @Query(value = "select * from like_dislike ld where ld.post_id = ?1 and ld.user_id = ?2 and ld.vote_type=?3", nativeQuery = true)
    LikeDislike findLikeDislikeObjByPostAndUser(Long post_id,Long user_id, int vote_id);

    List<LikeDislike> findAllByPost(Post post);
}
