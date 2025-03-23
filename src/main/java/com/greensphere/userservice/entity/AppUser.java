package com.greensphere.userservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppUser extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
    @SequenceGenerator(name = "user_sequence", sequenceName = "user_sequence", allocationSize = 1)
    private Long id;
    private Long govId;
    @Builder.Default
    private String username = UUID.randomUUID().toString();
    private String email;
    private String mobile;
    private String nic;
    private String password;
    private LocalDateTime disabledAt;
    private String disabledReason;
    private String fullName;
    private String addressNo;
    private String addressStreet;
    private String city;
    private String dob;
    private String profilePic;
    private LocalDateTime PasswordUpdatedAt;
    @Column(columnDefinition = "int default 0")
    private int loginAttempts;
    private String status;
    private String postalCode;
    private String roleType;
    private LocalDateTime registeredAt;

    private String otp;
    private String otpStatus;
    @Column(columnDefinition = "int default 0")
    private int verifyAttempts;
    private LocalDateTime otpSentAt;
    @Column(columnDefinition = "int default 0")
    private int otpAttempts;
    private boolean enabled;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    @Builder.Default
    private Collection<Role> roles = new ArrayList<>(); // Initialize roles


    @Transient
    private Collection<GrantedAuthority> grantedAuthoritiesList = new ArrayList<>();

    public void setPassword(String password) {
        this.password = new BCryptPasswordEncoder().encode(password);
    }

}
