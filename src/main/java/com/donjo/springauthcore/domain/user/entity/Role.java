package com.donjo.springauthcore.domain.user.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Role {
    /**
     * Primary key for the Role entity.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The user associated with this role.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id", nullable = false)
    private Users users;

    /**
     * The role assigned to the user.
     */
    @Enumerated(EnumType.STRING)
    private UserRoleEnum userRole;

    @Builder
    public Role(Users users, UserRoleEnum userRole) {
        this.users = users;
        this.userRole = userRole;
    }

    /**
     * Associates a user with this role.
     *
     * @param user the user to associate with this role
     * @throws IllegalStateException if a user is already associated with this role
     */
    public void setUsers(Users user) {
        if (this.users != null) {
            throw new IllegalStateException("User is already set for this role.");
        }
        this.users = user;
    }
}
