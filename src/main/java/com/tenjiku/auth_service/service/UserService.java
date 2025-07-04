package com.tenjiku.auth_service.service;

import com.tenjiku.auth_service.dto.entry_dto.user_registeration.LoginRequestDTO;
import com.tenjiku.auth_service.dto.entry_dto.user_registeration.PasswordDTO;
import com.tenjiku.auth_service.dto.entry_dto.user_registeration.UserDetailsDTO;
import com.tenjiku.auth_service.dto.exit_dto.user_registeration.LoginResponseDTO;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    LoginResponseDTO login(LoginRequestDTO loginRequest);

    LoginResponseDTO register(@Valid UserDetailsDTO userDetailsDTO);

    String updatePassword(@Valid PasswordDTO passwordDTO);
}
