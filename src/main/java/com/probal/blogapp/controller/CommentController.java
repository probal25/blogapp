package com.probal.blogapp.controller;

import com.probal.blogapp.model.Comment;
import com.probal.blogapp.model.Post;
import com.probal.blogapp.model.User;
import com.probal.blogapp.service.CommentService;
import com.probal.blogapp.service.PostService;
import com.probal.blogapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    UserService userService;

    @Autowired
    PostService postService;

    @Autowired
    CommentService commentService;

    @PostMapping("/create_comment/{post_id}")
    public String createComment(@PathVariable("post_id") Long postId,
                                Comment comment,
                                Principal principal){

        Post post = postService.getPostByPostId(postId);
        User user = userService.getUserByUserName(principal.getName());
        comment.setPost(post);
        comment.setUser(user);
        commentService.saveComment(comment);
        return "redirect:/post/view_post/" + comment.getPost().getId();

    }


}
