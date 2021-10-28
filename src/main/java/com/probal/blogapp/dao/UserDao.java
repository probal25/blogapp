package com.probal.blogapp.dao;

import com.probal.blogapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao extends JpaRepository<User, Long> {

    User findUserByUsername(String userName);

    @Query(value = "select u.*,\n" +
            "from users u\n" +
            "left join users_roles ur \n" +
            "    on u.user_id = ur.user_id \n" +
            "left join roles r\n" +
            "    on ur.role_id = r.role_id \n" +
            "    \n" +
            "where u.enabled = false and r.name = 'ROLE_BLOGGER'", nativeQuery = true)
    List<User> getNonApprovedUserList();

    @Query(value = "select u.*,\n" +
            "from users u\n" +
            "left join users_roles ur \n" +
            "    on u.user_id = ur.user_id \n" +
            "left join roles r\n" +
            "    on ur.role_id = r.role_id \n" +
            "    \n" +
            "where u.enabled = true and r.name = 'ROLE_ADMIN'", nativeQuery = true)
    List<User> getAdminUsers();

    @Query(value = "select u.*,\n" +
            "from users u\n" +
            "left join users_roles ur \n" +
            "    on u.user_id = ur.user_id \n" +
            "left join roles r\n" +
            "    on ur.role_id = r.role_id \n" +
            "    \n" +
            "where u.enabled = true and r.name = 'ROLE_BLOGGER'", nativeQuery = true)
    List<User> getBloggerUsers();


}
