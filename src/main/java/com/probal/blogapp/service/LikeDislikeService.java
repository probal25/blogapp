package com.probal.blogapp.service;


import com.probal.blogapp.dao.LikeDislikeDao;
import com.probal.blogapp.dao.PostDao;
import com.probal.blogapp.enums.VoteType;
import com.probal.blogapp.model.LikeDislike;
import com.probal.blogapp.model.Post;
import com.probal.blogapp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LikeDislikeService {

    @Autowired
    LikeDislikeDao likeDislikeDao;

    @Autowired
    PostDao postDao;

    public boolean likeCounter(Post post, User user) {
        if (!post.isApproved()) return false;

        LikeDislike likeObj = likeDislikeDao.findLikeDislikeObjByPostAndUser(post.getId(), user.getId(), VoteType.LIKE.getId());
        LikeDislike DislikeObj = likeDislikeDao.findLikeDislikeObjByPostAndUser(post.getId(), user.getId(), VoteType.DISLIKE.getId());

        if (likeObj != null) {
            if (!likeObj.getVoteType().equals(VoteType.LIKE)){
                post.setLikeCount(post.getLikeCount() + 1);
                postDao.save(post);
                return true;
            } else {
                return false;
            }
        }

        if (DislikeObj != null){
            likeDislikeDao.delete(DislikeObj);
            post.setDislikeCount(post.getDislikeCount()-1);
            postDao.save(post);
        }

        LikeDislike likeDislike = new LikeDislike();
        likeDislike.setUser(user);
        likeDislike.setPost(post);
        likeDislike.setVoteType(VoteType.LIKE);
        likeDislikeDao.save(likeDislike);
        post.setLikeCount(post.getLikeCount() + 1);
        postDao.save(post);
        return true;
    }

    public boolean dislikeCounter(Post post, User user) {
        if (!post.isApproved()) return false;

        LikeDislike DislikeObj = likeDislikeDao.findLikeDislikeObjByPostAndUser(post.getId(), user.getId(), VoteType.DISLIKE.getId());
        LikeDislike likeObj = likeDislikeDao.findLikeDislikeObjByPostAndUser(post.getId(), user.getId(), VoteType.LIKE.getId());

        if (DislikeObj != null) {
            if (!DislikeObj.getVoteType().equals(VoteType.DISLIKE)){
                post.setDislikeCount(post.getDislikeCount() + 1);
                postDao.save(post);
                return true;
            } else {
                return false;
            }
        }
        if (likeObj != null){
            likeDislikeDao.delete(likeObj);
            post.setLikeCount(post.getLikeCount()-1);
            postDao.save(post);
        }

        LikeDislike likeDislike = new LikeDislike();
        likeDislike.setUser(user);
        likeDislike.setPost(post);
        likeDislike.setVoteType(VoteType.DISLIKE);
        likeDislikeDao.save(likeDislike);
        post.setDislikeCount(post.getDislikeCount() + 1);
        postDao.save(post);
        return true;
    }
}
