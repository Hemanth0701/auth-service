package com.tenjiku.auth_service.mapper;

import com.tenjiku.auth_service.dto.exit_dto.user_registeration.AdminResponseDTO;
import com.tenjiku.auth_service.dto.exit_dto.user_registeration.DoctorResponseDTO;
import com.tenjiku.auth_service.dto.exit_dto.user_registeration.UserDetailsResponseDTO;
import com.tenjiku.auth_service.dto.exit_dto.user_registeration.UserResponseDTO;
import com.tenjiku.auth_service.entity.Admin;
import com.tenjiku.auth_service.entity.Doctor;
import com.tenjiku.auth_service.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsExitMapper {

    public UserDetailsResponseDTO toAdminDTO(Admin admin) {
        if ( admin == null ) {
            return null;
        }

        AdminResponseDTO adminResponseDTO = new AdminResponseDTO();
        adminResponseDTO.setId(admin.getId());
        adminResponseDTO.setUsername(admin.getUsername());
        adminResponseDTO.setEmail(admin.getEmail());
        adminResponseDTO.setPhoneNumber(admin.getPhoneNumber());
        adminResponseDTO.setRole(String.valueOf(admin.getRole()));
        adminResponseDTO.setCreatedAt(admin.getCreatedAt());

        return adminResponseDTO;
    }

    public UserDetailsResponseDTO toUserDTO(User user) {
        if ( user == null ) {
            return null;
        }

        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(user.getId());
        userResponseDTO.setUsername(user.getUsername());
        userResponseDTO.setEmail(user.getEmail());
        userResponseDTO.setPhoneNumber(user.getPhoneNumber());
        userResponseDTO.setRole(String.valueOf(user.getRole()));
        userResponseDTO.setCreatedAt(user.getCreatedAt());

        return userResponseDTO;
    }

    public UserDetailsResponseDTO toDoctorDTO(Doctor doctor) {
        if ( doctor == null ) {
            return null;
        }

        DoctorResponseDTO doctorResponseDTO= new DoctorResponseDTO();
        doctorResponseDTO.setId(doctor.getId());
        doctorResponseDTO.setUsername(doctor.getUsername());
        doctorResponseDTO.setEmail(doctor.getEmail());
        doctorResponseDTO.setPhoneNumber(doctor.getPhoneNumber());
        doctorResponseDTO.setRole(String.valueOf(doctor.getRole()));

        return doctorResponseDTO;
    }
}
