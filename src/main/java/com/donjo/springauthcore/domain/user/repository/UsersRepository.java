package com.donjo.springauthcore.domain.user.repository;

import com.donjo.springauthcore.domain.user.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {
    boolean existsByUsername(String username);
    boolean existsByNickname(String nickname);
    Optional<Users> findByUsername(String username);
}
