package com.donjo.springauthcore.domain.user.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id", nullable = false)
    private Users users;

    @Enumerated(EnumType.STRING)
    private UserRoleEnum userRole;

    @Builder
    public Role(Users users, UserRoleEnum userRole) {
        this.users = users;
        this.userRole = userRole;
    }
}
