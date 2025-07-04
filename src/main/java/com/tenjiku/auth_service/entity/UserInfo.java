package com.tenjiku.auth_service.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tenjiku.auth_service.entity.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@Builder
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String username;
    private String email;
    private String phoneNumber;
    private String password;


    @Enumerated(EnumType.STRING)
    private Role role;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @JsonIgnore
    private boolean isDeleted =false;
    @JsonIgnore
    private Instant deletedAt;
}
