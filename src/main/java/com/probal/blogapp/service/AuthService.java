package com.probal.blogapp.service;

import com.probal.blogapp.dao.UserDao;
import com.probal.blogapp.dto.JwtRequest;
import com.probal.blogapp.model.User;
import com.probal.blogapp.utill.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    UserDao userDao;

    public ResponseEntity<?> createAuthenticationToken(JwtRequest authenticationRequest) throws Exception {

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        try {
            final UserDetails userDetails = userDetailsServiceImpl
                    .loadUserByUsername(authenticationRequest.getUsername());
            final String token = jwtTokenUtil.generateToken(userDetails);
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("Authorization", "Bearer " + token);
            return ResponseEntity.ok().headers(responseHeaders).build();

        } catch(AuthenticationException e) {
            return ResponseEntity.badRequest().build();
        }

    }


    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    public boolean checkIfEnabledUser(String username) {
        User user = userDao.findUserByUsername(username);
        if (!user.isEnabled()){
            return false;
        }
        return true;
    }
}
