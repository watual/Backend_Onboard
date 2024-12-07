package com.donjo.springauthcore.domain.user.repository;

import com.donjo.springauthcore.domain.user.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Long> {
    boolean existsByUsername(String username);
}
