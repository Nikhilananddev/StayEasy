package com.nikhilanand.StayEase.controller;


import com.nikhilanand.StayEase.exchanges.request.AuthRequest;
import com.nikhilanand.StayEase.exchanges.request.RegisterRequest;
import com.nikhilanand.StayEase.exchanges.response.AuthResponse;
import com.nikhilanand.StayEase.global.GlobalVariables;
import com.nikhilanand.StayEase.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping(GlobalVariables.STAY_EASE_API_ENDPOINT)
public class AuthController {

    @Autowired
    AuthService authService;


    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @GetMapping("/home")
    public ResponseEntity<?> login() {
        return ResponseEntity.ok().body("Hello");
    }


}
