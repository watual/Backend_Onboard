package com.donjo.springauthcore.domain.user.repository;

import com.donjo.springauthcore.domain.user.entity.Role;
import com.donjo.springauthcore.domain.user.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository for managing Role entities.
 */
public interface RoleRepository extends JpaRepository<Role, Long> {

    /**
     * Finds all roles associated with a specific user.
     *
     * @param users the user entity
     * @return a list of roles associated with the user
     */
    List<Role> findByUsers(Users users);
}
