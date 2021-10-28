package com.probal.blogapp.service;

import com.probal.blogapp.dao.RoleDao;
import com.probal.blogapp.dao.UserDao;
import com.probal.blogapp.dto.RegistrationDto;
import com.probal.blogapp.model.Role;
import com.probal.blogapp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class RegistrationService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    PasswordEncoder passwordEncoder;

    public void registerUser(RegistrationDto registrationDto) throws Exception {
        Set<Role> roleSet = new HashSet<>();
        Role role = roleDao.getRoleByName("ROLE_BLOGGER");
        roleSet.add(role);
        User user = new User();
        user.setUsername(registrationDto.getUserName());
        user.setPassword(encodePassword(registrationDto.getUserPassword()));
        user.setEnabled(false);
        user.setRoles(roleSet);
        userDao.save(user);
    }

    public void registerAdminUser(User user) throws Exception {
        Set<Role> roleSet = new HashSet<>();
        Role role = roleDao.getRoleByName("ROLE_ADMIN");
        roleSet.add(role);
        user.setPassword(encodePassword(user.getPassword()));
        user.setEnabled(true);
        user.setRoles(roleSet);
        userDao.save(user);
    }



    public boolean checkIfUserExist(String userName) {
        User user = userDao.findUserByUsername(userName);
        if (user != null) return true;
        return false;
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}
