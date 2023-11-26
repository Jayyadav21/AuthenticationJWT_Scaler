package com.example.authnamansir.controllers;

import com.example.authnamansir.dtos.SetUserRolesRequestDto;
import com.example.authnamansir.dtos.UserDto;
import com.example.authnamansir.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


public class UserController {

    UserService userService;

        public UserController(UserService userService) {
            this.userService = userService;
        }

//        @GetMapping("/{id}")
//        public ResponseEntity<UserDto> getUserDetails(@PathVariable("id") Long userId){
//        UserDto userDto = userService;
//
//        return new ResponseEntity<>(userDto, HttpStatus.OK);
//    }


//        @PostMapping("/{id}/roles")
//        public ResponseEntity<UserDto> setUserRoles(@PathVariable("id") Long userId, @RequestBody SetUserRolesRequestDto){
//            UserDto userDto=userService.setUserRoles(userId);
//            return new ResponseEntity<>(userDto,HttpStatus.OK);
//        }
    }
