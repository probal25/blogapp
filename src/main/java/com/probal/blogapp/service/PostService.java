package com.probal.blogapp.service;

import com.probal.blogapp.dao.LikeDislikeDao;
import com.probal.blogapp.dao.PostDao;
import com.probal.blogapp.dao.UserDao;
import com.probal.blogapp.model.LikeDislike;
import com.probal.blogapp.model.Post;
import com.probal.blogapp.model.Role;
import com.probal.blogapp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    PostDao postDao;

    @Autowired
    LikeDislikeDao likeDislikeDao;

    @Autowired
    UserService userService;

    public void savePost(Post post) {
        User user = post.getUser();
        if (userService.isAdminUser(user)){
            post.setApproved(true);
            postDao.save(post);
        } else {
            post.setApproved(false);
            postDao.save(post);
        }

    }

    public List<Post> allApprovedPosts() {
        return postDao.findAllApprovedPosts();
    }

    public List<Post> allPostByUser(long userId){
        return postDao.allPostByUserId(userId);
    }


    public List<Post> findAllNonApprovedPosts(){
        return postDao.findAllNonApprovedPosts();
    }


    public Post getPostByPostId(Long postId) {
        return postDao.getById(postId);
    }


    public void approvePost(Long postId) {
        Post post = postDao.getById(postId);
        post.setApproved(true);
        postDao.save(post);
    }

    @Transactional
    public void deletePost(Post post) {
        List<LikeDislike> likeDislikes = likeDislikeDao.findAllByPost(post);
        if (likeDislikes.size() > 0) {
            for (LikeDislike likeDislike : likeDislikes) {
                likeDislikeDao.delete(likeDislike);
            }
        }
        postDao.delete(post);
    }


    public boolean isLoggedInUserOwnerOfThisPost(Post post, Principal principal) {
        return principal != null && principal.getName().equals(post.getUser().getUsername());
    }

    public List<Post> findAllPosts() {
        return postDao.findAll();
    }
}
