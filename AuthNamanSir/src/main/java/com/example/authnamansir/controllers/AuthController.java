package com.example.authnamansir.controllers;

import com.example.authnamansir.dtos.*;
import com.example.authnamansir.exceptions.UserAlreadyExistsException;
import com.example.authnamansir.exceptions.UserDoesNotExistException;
import com.example.authnamansir.models.SessionStatus;
import com.example.authnamansir.models.User;
import com.example.authnamansir.repositories.UserRepository;
import com.example.authnamansir.services.AuthService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.MultiValueMapAdapter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private AuthService authService;
    private final UserRepository userRepository;

    public AuthController(AuthService authService,
                          UserRepository userRepository) {
        this.authService = authService;
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody LoginRequestDto request) throws UserDoesNotExistException {
            return authService.login(request.getEmail(),request.getPassword());
   }
//    @PostMapping("/logout")
//    public ResponseEntity<Void> logout(@RequestBody LogoutRequestDto request){
//        return authService.logout();
//    }

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signUp(@RequestBody SignUpRequestDto request) throws UserAlreadyExistsException {
        UserDto userDto=authService.signUp(request.getEmail(), request.getPassword());
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @PostMapping("/validate")
    public ResponseEntity<SessionStatus> validateToken(@RequestBody ValidateTokenRequestDto request){
        SessionStatus sessionStatus=authService.validate(request.getToken(), request.getUserId() );
        return new ResponseEntity<>(sessionStatus,HttpStatus.OK);
    }


}
