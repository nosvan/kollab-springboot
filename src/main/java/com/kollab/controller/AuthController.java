package com.kollab.controller;

import com.kollab.dto.UserDto;
import com.kollab.entity.User;
import com.kollab.service.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.http.HttpResponse;

@RestController
public class AuthController {
    private final UserServiceImpl userServiceImpl;
    private final AuthenticationManager authenticationManager;
    private final SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();

    @Autowired
    public AuthController(UserServiceImpl userServiceImpl, AuthenticationManager authenticationManager){
        this.userServiceImpl = userServiceImpl;
        this.authenticationManager = authenticationManager;
    }

    @GetMapping("/logoutSuccess")
    public ResponseEntity<?> logoutSuccess(){
        return new ResponseEntity<>("User logged out", HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> RegisterUser(@RequestBody @Valid UserDto registeringUser){
        System.out.println("in register controller");
        User existingUser = userServiceImpl.findUserByEmail(registeringUser.getEmail());
        if(existingUser != null){
            return new ResponseEntity<>("User already exists", HttpStatus.CONFLICT);
        }
        userServiceImpl.saveUser(registeringUser);
        return new ResponseEntity<>("User created", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserDto user, HttpServletRequest request, HttpServletResponse response, HttpSession session){
        UsernamePasswordAuthenticationToken token = UsernamePasswordAuthenticationToken.unauthenticated(
                user.getEmail(), user.getPassword());
        Authentication authentication = authenticationManager.authenticate(token);
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
        securityContextRepository.saveContext(context, request, response);
        return new ResponseEntity<>("User signed-in successfully!.", HttpStatus.OK);
    }
}
