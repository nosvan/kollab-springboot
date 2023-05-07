package com.kollab.controller;

import com.kollab.dto.UserDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

public class AuthController {
    @PostMapping("/register")
    public String RegisterUser(UserDto model){
    }
}
