package com.probal.blogapp.config;
/*
* This class is for creating an admin user
* in startup of the application
* */
import com.probal.blogapp.dao.RoleDao;
import com.probal.blogapp.dao.UserDao;
import com.probal.blogapp.model.Role;
import com.probal.blogapp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {

    @Autowired
    UserDao userDao;

    @Autowired
    RoleDao roleDao;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        try {
            setRoles();
            setAdminUser();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setRoles(){
        List<Role> roles = Arrays.asList(
                new Role("ROLE_ADMIN"),
                new Role("ROLE_BLOGGER")
        );
        for (Role role: roles) {
            roleDao.save(role);
        }
    }

    private void setAdminUser(){
        Set<Role> roleSet = new HashSet<>();
        Role role = roleDao.getRoleByName("ROLE_ADMIN");
        roleSet.add(role);
        String username = "admin";
        String password = passwordEncoder.encode("12345");
        boolean isEnabled = true;
        User user = new User(username, password, isEnabled, roleSet);

        userDao.save(user);
    }
}
