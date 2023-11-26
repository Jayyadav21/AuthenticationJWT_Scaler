package com.example.authnamansir.services;

import com.example.authnamansir.dtos.UserDto;
import com.example.authnamansir.exceptions.UserAlreadyExistsException;
import com.example.authnamansir.exceptions.UserDoesNotExistException;
import com.example.authnamansir.models.SessionStatus;
import com.example.authnamansir.models.User;
import org.springframework.http.ResponseEntity;

public interface AuthService {


    public UserDto signUp(String email,String password) throws UserAlreadyExistsException;

    public ResponseEntity<UserDto> login(String email, String password) throws UserDoesNotExistException;

    public SessionStatus validate(String token,Long userId);

    public ResponseEntity<Void> logout(String token,Long userId);
}
