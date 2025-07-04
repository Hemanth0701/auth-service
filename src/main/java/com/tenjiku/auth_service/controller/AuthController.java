package com.tenjiku.auth_service.controller;

import com.tenjiku.auth_service.dto.entry_dto.user_registeration.LoginRequestDTO;
import com.tenjiku.auth_service.dto.entry_dto.user_registeration.PasswordDTO;
import com.tenjiku.auth_service.dto.entry_dto.user_registeration.UserDetailsDTO;
import com.tenjiku.auth_service.dto.exit_dto.user_registeration.LoginResponseDTO;
import com.tenjiku.auth_service.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
@Validated
public class AuthController {

    private final UserService userService;

    @PostMapping(value = "/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequest) {

        return ResponseEntity.ok(userService.login(loginRequest));
    }

    @PostMapping(value = "/register")
    public ResponseEntity<LoginResponseDTO> userRegister(@Valid @RequestBody UserDetailsDTO userDetailsDTO){

        LoginResponseDTO response = userService.register(userDetailsDTO); // return both token + user

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{username}")
                .buildAndExpand(response.getUserDetailsResponse().getUsername())
                .toUri();

        return ResponseEntity.created(location).body(response);// if there is no any other instance
    }
    @PatchMapping(value = "/forgotPassword")
    // @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<String> updateUserPassword(@Valid @RequestBody PasswordDTO passwordDTO){
        return ResponseEntity.ok(userService.updatePassword(passwordDTO));
    }

//    @PostMapping("/refresh")
//    public ResponseEntity<ResponseStructure<AuthResponse>> refresh(HttpServletRequest request){
//        AuthenticatedTokenDetails tokenDetails = (AuthenticatedTokenDetails) request.getAttribute("tokenDetails");
//        log.debug("Authenticated token details, email:{}, role:{}", tokenDetails.email(), tokenDetails.role());
//        AuthResponse authResponse = authService.refresh(tokenDetails);
//        return responseBuilder.sucess(HttpStatus.OK, "Login Successful", authResponse);
//    }
}
