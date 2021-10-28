package com.probal.blogapp.dao;

import com.probal.blogapp.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleDao extends JpaRepository<Role, Long> {
    Role getRoleByName(String name);
}
