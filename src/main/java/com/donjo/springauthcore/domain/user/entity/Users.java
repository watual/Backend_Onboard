package com.donjo.springauthcore.domain.user.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Entity representing a user in the system.
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Users {
    /**
     * Primary key for the Users entity.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * User's unique username.
     */
    @Column(nullable = false, unique = true)
    private String username;

    /**
     * User's password (stored securely in hashed format).
     */
    @Column(nullable = false)
    private String password;

    /**
     * User's unique nickname.
     */
    @Column(nullable = false, unique = true)
    private String nickname;

    /**
     * List of roles associated with the user.
     */
    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Role> roleList = new ArrayList<>();

    /**
     * Refresh token for the user session.
     */
    @Column(name = "refresh_token")
    private String refreshToken;

    @Builder
    public Users(String username, String password, String nickname) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
    }

    /**
     * Adds a role to the user's role list and establishes a bidirectional relationship.
     *
     * @param role the role to add
     */
    public void addRoleList(Role role) {
        if (roleList == null) {
            roleList = new ArrayList<>();
        }
        roleList.add(role);
        role.setUsers(this); // 양방향 연관 관계 설정
    }

    /**
     * Updates the user's refresh token.
     *
     * @param refreshToken the new refresh token
     */
    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}

