package com.donjo.springauthcore.domain.user.repository;

import com.donjo.springauthcore.domain.user.entity.Role;
import com.donjo.springauthcore.domain.user.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {
    List<Role> findByUsers(Users users);
}
