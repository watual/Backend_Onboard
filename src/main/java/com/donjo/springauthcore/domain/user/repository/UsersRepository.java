package com.donjo.springauthcore.domain.user.repository;

import com.donjo.springauthcore.domain.user.entity.Users;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository for managing Users entities.
 */
public interface UsersRepository extends JpaRepository<Users, Long> {

    /**
     * Checks if a user with the given username exists.
     *
     * @param username the username to check
     * @return true if the username exists, false otherwise
     */
    @Cacheable("users")
    boolean existsByUsername(String username);

    /**
     * Checks if a user with the given nickname exists.
     *
     * @param nickname the nickname to check
     * @return true if the nickname exists, false otherwise
     */
    @Cacheable("users")
    boolean existsByNickname(String nickname);

    /**
     * Finds a user by their username.
     *
     * @param username the username to search for
     * @return an Optional containing the user if found, or empty if not
     */
    Optional<Users> findByUsername(String username);
}
