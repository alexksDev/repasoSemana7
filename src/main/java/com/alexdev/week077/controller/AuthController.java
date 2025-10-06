package com.alexdev.week077.controller;

import com.alexdev.week077.dto.AuthToken;
import com.alexdev.week077.dto.LoginDTO;
import com.alexdev.week077.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthToken> login(@RequestBody LoginDTO loginDTO) {
        String token = authService.login(loginDTO.getEmail(), loginDTO.getPassword());
        return ResponseEntity.ok(new AuthToken(token));
    }
}
