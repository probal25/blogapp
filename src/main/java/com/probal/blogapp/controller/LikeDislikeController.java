package com.probal.blogapp.controller;


import com.probal.blogapp.model.Post;
import com.probal.blogapp.model.User;
import com.probal.blogapp.service.LikeDislikeService;
import com.probal.blogapp.service.PostService;
import com.probal.blogapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/vote")
public class LikeDislikeController {

    @Autowired
    private LikeDislikeService likeDislikeService;

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @GetMapping("/like/{post_id}")
    public String giveLikeToPost(@PathVariable("post_id") Long postId, Principal principal, Model model){
        Post post = postService.getPostByPostId(postId);
        User user = userService.getUserByUserName(principal.getName());
        boolean isEligible = likeDislikeService.likeCounter(post, user);
        if (isEligible){
            model.addAttribute("message", "Like has been added");
        }else {
            model.addAttribute("message", "You have already given a like to this post");
        }
        return "redirect:/post/view_post/" + postId;
    }

    @GetMapping("/dislike/{post_id}")
    public String giveDislikeToPost(@PathVariable("post_id") Long postId, Principal principal, Model model){
        Post post = postService.getPostByPostId(postId);
        User user = userService.getUserByUserName(principal.getName());
        boolean isEligible = likeDislikeService.dislikeCounter(post, user);
        if (isEligible){
            model.addAttribute("message", "dislike has been added");
        }else {
            model.addAttribute("message", "You have already given a dislike to this post");
        }
        return "redirect:/post/view_post/" + postId;
    }
}
