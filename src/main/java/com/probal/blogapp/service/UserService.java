package com.probal.blogapp.service;


import com.probal.blogapp.dao.UserDao;
import com.probal.blogapp.model.Role;
import com.probal.blogapp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    UserDao userDao;

    public List<User> getAdminUserList(){
        return userDao.getAdminUsers();
    }

    public List<User> getBloggerUserList(){
        return userDao.getBloggerUsers();
    }



    public List<User> getNonApprovedUserList(){
        return userDao.getNonApprovedUserList();
    }

    public void approveUser(Long userId) {
        User user  = userDao.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        user.setEnabled(true);
        userDao.save(user);
    }
    public void disableUser(Long userId) {
        User user  = userDao.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (!isAdminUser(user)) {
            user.setEnabled(false);
            userDao.save(user);
        }
    }


    public User getUserByUserName(String userName) {
        return userDao.findUserByUsername(userName);
    }

    public boolean isLoggedInUserIsAdmin(Principal principal){
        User user = userDao.findUserByUsername(principal.getName());
        return isAdminUser(user);
    }

    public boolean isAdminUser(User user) {
        return getUserRoles(user).contains("ROLE_ADMIN");
    }

    private List<String> getUserRoles(User user){
        List<Role> roles = new ArrayList<>(user.getRoles());
        List<String> roleNames = roles.stream().map(role -> role.getName()).collect(Collectors.toList());
        return roleNames;
    }


}
