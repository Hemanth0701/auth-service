package com.tenjiku.auth_service.dto.exit_dto.user_registeration;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "role")
@JsonSubTypes({
        @JsonSubTypes.Type(value = UserResponseDTO.class, name = "USER"),
        @JsonSubTypes.Type(value = DoctorResponseDTO.class, name = "ADMIN"),
        @JsonSubTypes.Type(value = DoctorResponseDTO.class, name = "DOCTOR")
})
public class UserDetailsResponseDTO {
    private String id;
    private String username;
    private String email;
    private String phoneNumber;
    private String role;
    private LocalDateTime createdAt;

}

