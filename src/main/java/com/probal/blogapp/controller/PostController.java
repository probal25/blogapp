package com.probal.blogapp.controller;

import com.probal.blogapp.dao.UserDao;
import com.probal.blogapp.model.Comment;
import com.probal.blogapp.model.Post;
import com.probal.blogapp.model.User;
import com.probal.blogapp.service.PostService;
import com.probal.blogapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/post")
public class PostController {

    @Autowired
    PostService postService;

    @Autowired
    UserService userService;

    @Autowired
    UserDao userDao;


    @GetMapping("/new_post")
    public String newPostForm(Principal principal, Model model) {
        User user = userService.getUserByUserName(principal.getName());

        if (user != null) {
            Post post = new Post();
            post.setUser(user);

            model.addAttribute("post", post);

            return "post/post_form";
        } else {
            return "";
        }
    }

    @GetMapping("/my_posts")
    public String getMyPosts(Principal principal, Model model) {
        User user = userDao.findUserByUsername(principal.getName());
        List<Post> userPostList = postService.allPostByUser(user.getId());
        model.addAttribute("my_posts", userPostList);
        return "post/my_posts";

    }

    @PostMapping("/new_post")
    public String createNewPost(@Valid Post post, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "post/post_form";
        } else {
            postService.savePost(post);
            return "redirect:/home";
        }
    }

    @GetMapping("/view_post/{post_id}")
    public String viewPost(@PathVariable("post_id") Long postId, Model model) {
        Post post = postService.getPostByPostId(postId);
        if (post != null) {
            model.addAttribute("post", post);
            model.addAttribute("comment", new Comment());
            return "post/post_view";
        }
        return "redirect:/home";
    }

    @GetMapping("/all_non_approved_posts")
    public String findAllNonApprovedPosts(Model model){
        List<Post> nonApprovedPosts = postService.findAllNonApprovedPosts();

        model.addAttribute("all_post", nonApprovedPosts);
        return "post/non_approved_posts";
    }

    @GetMapping("/all_posts")
    public String findAllPosts(Model model){
        List<Post> allPosts = postService.findAllPosts();

        model.addAttribute("my_posts", allPosts);
        return "post/my_posts";
    }



    @GetMapping("/approve_post/{id}")
    public String approvePost(@PathVariable("id") Long postId, Model model){
        postService.approvePost(postId);
        return "redirect:/post/all_non_approved_posts";
    }

    @GetMapping("/edit_post/{post_id}")
    public String editPost(@PathVariable("post_id") Long postId,
                           Model model,
                           Principal principal) {
        Post post = postService.getPostByPostId(postId);

        if (post != null) {
            if (postService.isLoggedInUserOwnerOfThisPost(post, principal)){
                model.addAttribute("post", post);
                return "post/post_form";
            } else {
                return "/403";
            }
        }
        return "/error";
    }


    @GetMapping("/delete_post/{post_id}")
    public String deletePost(@PathVariable("post_id") Long postId,
                             Model model,
                             Principal principal){
        Post post = postService.getPostByPostId(postId);

        if (post != null) {
            if (postService.isLoggedInUserOwnerOfThisPost(post, principal) || userService.isLoggedInUserIsAdmin(principal)){
                postService.deletePost(post);
                return "redirect:/post/my_posts";
            } else {
                return "/403";
            }
        }
        return "/error";
    }






}
