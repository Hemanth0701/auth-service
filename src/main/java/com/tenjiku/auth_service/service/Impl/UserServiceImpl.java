package com.tenjiku.auth_service.service.Impl;

import com.tenjiku.auth_service.dto.entry_dto.user_registeration.*;
import com.tenjiku.auth_service.dto.exit_dto.user_registeration.LoginResponseDTO;
import com.tenjiku.auth_service.dto.exit_dto.user_registeration.UserDetailsResponseDTO;
import com.tenjiku.auth_service.entity.Admin;
import com.tenjiku.auth_service.entity.Doctor;
import com.tenjiku.auth_service.entity.User;
import com.tenjiku.auth_service.entity.UserInfo;
import com.tenjiku.auth_service.exception.InternalServerErrorException;
import com.tenjiku.auth_service.exception.UserAlreadyExistsException;
import com.tenjiku.auth_service.exception.UserNotFoundException;
import com.tenjiku.auth_service.mapper.UserDetailsEntryMapper;
import com.tenjiku.auth_service.mapper.UserDetailsExitMapper;
import com.tenjiku.auth_service.repos.UserRepo;
import com.tenjiku.auth_service.security.CustomUserDetails;
import com.tenjiku.auth_service.security.JwtUtil;
import com.tenjiku.auth_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final JwtUtil jwtUtil;
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsEntryMapper userDetailsEntryMapper;
    private final UserDetailsExitMapper userDetailsExitMapper;
    private final AuthenticationManager authenticationManager;
    @Override
    public LoginResponseDTO login(LoginRequestDTO loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getPhoneNumberOrEmail(),
                        loginRequest.getPassword())
        );

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        UserInfo appUser = userDetails.getUser();

        UserDetailsResponseDTO responseDTO = switch (appUser) {
            case User user -> userDetailsExitMapper.toUserDTO(user);
            case Admin admin -> userDetailsExitMapper.toAdminDTO(admin);
            case Doctor doctor -> userDetailsExitMapper.toDoctorDTO(doctor);
            default -> throw new IllegalStateException("Unexpected user type: " + appUser.getClass().getSimpleName());
        };

        String token = jwtUtil.generateToken(userDetails);

        // Return a combined DTO including token and user details
        return new LoginResponseDTO(token, responseDTO);
    }

    @Override
    public LoginResponseDTO register(UserDetailsDTO userDetailsDTO) {

        UserDetailsDTO eventUser= userDetailsDTO;

        //  check if an email or PhoneNumber already exist
        if (userRepo.existsByPhoneNumber(userDetailsDTO.getPhoneNumber()) || userRepo.existsByEmail(userDetailsDTO.getEmail())) {
            throw new UserAlreadyExistsException(" PhoneNumber or email already exists");
        }

        UserInfo savedUser;
        UserDetailsResponseDTO responseDTO;


        switch (userDetailsDTO) {
            case AdminDTO adminDTO -> {
                Admin admin = userDetailsEntryMapper.toAdmin(adminDTO);
                admin.setPassword(passwordEncoder.encode(admin.getPassword()));
                savedUser = userRepo.save(admin);
                responseDTO = userDetailsExitMapper.toAdminDTO(admin);
            }
            case UserDTO userDTO -> {
                User user = userDetailsEntryMapper.toUser(userDTO);
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                savedUser = userRepo.save(user);
                responseDTO = userDetailsExitMapper.toUserDTO(user);
            }
            case DoctorDTO doctorDTO -> {
                Doctor doctor = userDetailsEntryMapper.toDoctor(doctorDTO);
                doctor.setPassword(passwordEncoder.encode(doctor.getPassword()));
                savedUser = userRepo.save(doctor);
                responseDTO = userDetailsExitMapper.toDoctorDTO(doctor);
            }
            default ->
                    throw new InternalServerErrorException("Unexpected user type: " + userDetailsDTO.getClass().getSimpleName());
        }

        CustomUserDetails userDetails = new CustomUserDetails(savedUser);
        String token = jwtUtil.generateToken(userDetails);
        return new LoginResponseDTO(token, responseDTO);
    }

    @Override
    public String updatePassword(PasswordDTO passwordDTO) {
        // Fetch user by ID
        UserInfo user = userRepo.findById(passwordDTO.getId())
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + passwordDTO.getId()));

        // set new password
        user.setPassword(passwordEncoder.encode(passwordDTO.getPassword()));

        // Save updated user
        userRepo.save(user);

        return " Password Updated successfully " ;
    }
}
