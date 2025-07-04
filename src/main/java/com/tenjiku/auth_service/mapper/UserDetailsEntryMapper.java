package com.tenjiku.auth_service.mapper;


import com.tenjiku.auth_service.dto.entry_dto.user_registeration.AdminDTO;
import com.tenjiku.auth_service.dto.entry_dto.user_registeration.DoctorDTO;
import com.tenjiku.auth_service.dto.entry_dto.user_registeration.UserDTO;
import com.tenjiku.auth_service.entity.Admin;
import com.tenjiku.auth_service.entity.Doctor;
import com.tenjiku.auth_service.entity.User;
import com.tenjiku.auth_service.entity.enums.Role;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsEntryMapper {

    public Admin toAdmin(AdminDTO adminDTO) {
        if ( adminDTO == null ) {
            return null;
        }

        Admin admin =new Admin();
        admin.setUsername(adminDTO.getUsername());
        admin.setEmail(adminDTO.getEmail());
        admin.setPassword(adminDTO.getPassword());
        admin.setPhoneNumber(adminDTO.getPhoneNumber());

        admin.setRole(Role.ADMIN);

        return admin;
    }

    public User toUser(UserDTO userDTO) {
        if ( userDTO == null ) {
            return null;
        }

        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setRole(Role.USER);
        return user;
    }

    public Doctor toDoctor(DoctorDTO doctorDTO) {
        if ( doctorDTO == null ) {
            return null;
        }

        Doctor doctor= new Doctor();
        doctor.setUsername(doctorDTO.getUsername());
        doctor.setEmail(doctorDTO.getEmail());
        doctor.setPassword(doctorDTO.getPassword());
        doctor.setPhoneNumber(doctorDTO.getPhoneNumber());
        doctor.setRole(Role.DOCTOR);
        return doctor;
    }

}
