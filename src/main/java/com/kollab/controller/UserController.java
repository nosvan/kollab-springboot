package com.kollab.controller;

import com.kollab.dto.UserDto;
import com.kollab.service.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    private final UserServiceImpl userServiceImpl;

    @Autowired
    public UserController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @GetMapping("/api/users")
    public ResponseEntity<?> getUsers(HttpSession session){
        System.out.println("in users controller");
        List<UserDto> users = userServiceImpl.findAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
