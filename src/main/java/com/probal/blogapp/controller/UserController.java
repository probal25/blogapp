package com.probal.blogapp.controller;


import com.probal.blogapp.model.User;
import com.probal.blogapp.service.RegistrationService;
import com.probal.blogapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    RegistrationService registrationService;

    @GetMapping("/create_admin_user")
    public String createAdminUser(Model model){
        model.addAttribute("admin_user", new User());
        return "user/create_admin";
    }

    @PostMapping("/create_admin")
    public String createAdmin(@Valid @ModelAttribute(value="admin_user") User user,
                              final BindingResult bindingResult,
                              final Model model) throws Exception {

        if (registrationService.checkIfUserExist(user.getUsername())){
            bindingResult
                    .rejectValue("username", "error.admin_user",
                            "There is already an user account registered with this Username");
        }

        if(!bindingResult.hasErrors()){
            registrationService.registerAdminUser(user);
            model.addAttribute("successMessage", "An Admin account has been registered successfully");
            model.addAttribute("admin_user", new User());
        }
        return "user/create_admin";
    }

    @GetMapping("/admin_users")
    public String getAllAdminUsers(Model model) {

        model.addAttribute("all_user", userService.getAdminUserList());
        return "user/admin_users";
    }

    @GetMapping("/blogger_users")
    public String getAllBloggerUsers(Model model) {

        model.addAttribute("all_user", userService.getBloggerUserList());
        return "user/blogger_users";
    }



    @GetMapping("/user_approval")
    public String getNonApprovedUserList(Model model) {

        model.addAttribute("all_user", userService.getNonApprovedUserList());
        return "user/notApprovedUsers";
    }

    @GetMapping("/user_approval/{id}")
    public String approveUser(@PathVariable("id") Long userId) {
        try{
            userService.approveUser(userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/users/blogger_users";
    }

    @GetMapping("/deactivate_user/{id}")
    public String deactivateUser(@PathVariable("id") Long userId) {
        try{
            userService.disableUser(userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/users/user_approval";
    }




}
