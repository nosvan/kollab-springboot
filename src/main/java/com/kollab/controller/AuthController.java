package com.kollab.controller;

import com.kollab.dto.UserDto;
import com.kollab.entity.User;
import com.kollab.service.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    private final UserServiceImpl userServiceImpl;
    public AuthController(UserServiceImpl userService){
        this.userServiceImpl = userService;
    }
    @PostMapping("/register")
    public ResponseEntity<?> RegisterUser(@Valid UserDto model){
        System.out.println("in register controller");
        User existingUser = userServiceImpl.findUserByEmail(model.getEmail());
        if(existingUser != null && existingUser.getEmail() != null && existingUser.getEmail().isEmpty()){
            return new ResponseEntity<>("User already exists", HttpStatus.CONFLICT);
        }
        userServiceImpl.saveUser(model);
        return new ResponseEntity<>("User created", HttpStatus.OK);
    }
}
