package com.probal.blogapp.dto;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

public class JwtRequest implements Serializable {
    private static final long serialVersionUID = 5926468583005150707L;
    @NotEmpty(message = "*Please provide your Username")
    private String username;
    @NotEmpty(message = "*Please provide your password")
    private String password;

    //need default constructor for JSON Parsing
    public JwtRequest() { }

    public JwtRequest(String username, String password) {
        this.setUsername(username);
        this.setPassword(password);
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

