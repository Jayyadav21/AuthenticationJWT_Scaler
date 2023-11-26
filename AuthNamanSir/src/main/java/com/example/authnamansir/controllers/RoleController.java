package com.example.authnamansir.controllers;

import com.example.authnamansir.dtos.CreateRoleRequestDto;
import com.example.authnamansir.models.Role;
import com.example.authnamansir.services.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/roles")
public class RoleController {

    private RoleService roleService;

//    public ResponseEntity<Role> createRole(CreateRoleRequestDto request){
//        Role role=roleService.createRole(request.getName());
//        return new ResponseEntity<>(role, HttpStatus.OK);
//    }
}
