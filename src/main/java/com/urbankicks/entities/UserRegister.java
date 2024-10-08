package com.urbankicks.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class UserRegister {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String firstname;

    @Column(nullable = false)
    private String lastname;

    @Column(columnDefinition = "TEXT")
    private String address;

    private String mobile;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @ManyToOne
    @JoinColumn(name = "state_id")
    private State state;

    @ManyToOne
    @JoinColumn(name = "district_id")
    private District district;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Boolean isLoggedOut;

    private LocalDateTime lastLoggedIn;

    public enum Role {
        CUSTOMER, ADMIN
    }

    public String getFullName() {
        return firstname + " " + lastname;
    }
}
