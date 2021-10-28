package com.probal.blogapp.controller;

import com.probal.blogapp.dto.JwtRequest;
import com.probal.blogapp.dto.RegistrationDto;
import com.probal.blogapp.service.AuthService;
import com.probal.blogapp.service.PostService;
import com.probal.blogapp.service.RegistrationService;
import com.probal.blogapp.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.logging.Logger;

@CrossOrigin(origins = "http://localhost:8000" , exposedHeaders = "Authorization")
@Controller
public class AuthController {


    @Autowired
    UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private AuthService authService;

    @Autowired
    private PostService postService;

    final Logger logger = Logger.getLogger(AuthController.class.getName());

    @GetMapping("/")
    public String index() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.getPrincipal().equals("anonymousUser")) {
            return "redirect:/home";
        }
        return "redirect:/login";
    }

    @GetMapping("/home")
    public String homeView(Model model) {
        /*Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.getPrincipal().equals("anonymousUser")) {
            return "redirect:/login";
        }*/
        model.addAttribute("all_post", postService.allApprovedPosts());
        return "post/home";
    }

    @GetMapping("/login")
    public String loginView(Model model) {
        model.addAttribute("authenticationRequest", new JwtRequest());
        return "account/login";
    }

    @PostMapping("/process_login")
    public String loginAuth(@ModelAttribute(value="authenticationRequest") JwtRequest authenticationRequest,
                            final BindingResult bindingResult,
                            HttpServletRequest request, HttpSession session,
                            @Valid @ModelAttribute(value="authenticationRequest") JwtRequest jwtRequest) throws Exception {
        if (!registrationService.checkIfUserExist(authenticationRequest.getUsername())){
            bindingResult
                    .rejectValue("username", "error.authenticationRequest",
                            "User does not exist");
        }

        if (registrationService.checkIfUserExist(authenticationRequest.getUsername())) {
            if (!authService.checkIfEnabledUser(authenticationRequest.getUsername())) {
                bindingResult
                        .rejectValue("username", "error.authenticationRequest",
                                "Wait for user approval from Admin");
            }
        }


        if(!bindingResult.hasErrors()){

            session.invalidate();

            ResponseEntity<?> authResponse = authService.createAuthenticationToken(authenticationRequest);
            if (authResponse.getStatusCode() == HttpStatus.OK) {
                String tokenObj = authResponse.getHeaders().get("Authorization").get(0);
                HttpSession newSession = request.getSession(); // create session
                newSession.setAttribute("token", tokenObj);

                System.err.println(tokenObj);
                return "redirect:/home";
            } else {
                return "redirect:/login";
            }
        }
        List<FieldError> errors = bindingResult.getFieldErrors();
        for (FieldError error : errors ) {
            logger.info("*********************"+error.getObjectName() + " ---> " + error.getDefaultMessage() + "*********************");
        }
        return "redirect:/login";
    }

    @GetMapping("/register")
    private String registerView(final Model model){
        model.addAttribute("userData", new RegistrationDto());
        return "account/register";
    }

    @PostMapping("/register")
    public String register(final @Valid @ModelAttribute(value="userData") RegistrationDto registrationDto,
                           final BindingResult bindingResult,
                           final Model model) throws Exception {

        if (registrationService.checkIfUserExist(registrationDto.getUserName())){
            bindingResult
                    .rejectValue("userName", "error.userData",
                            "There is already an user registered with this Username");
        }

        if(!bindingResult.hasErrors()){
            registrationService.registerUser(registrationDto);
            model.addAttribute("successMessage", "A Blogger account has been registered successfully! Wait for the approval");
            model.addAttribute("userData", new RegistrationDto());
        }
        return "account/register";
    }

}
