package com.alexdev.week077.service;

import com.alexdev.week077.entity.User;
import com.alexdev.week077.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public String login(String email, String password) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );
        } catch (AuthenticationException e) {
            throw new RuntimeException("Invalid credentials");
        }

        User user = userService.findByEmail(email);
        return jwtService.generateToken(user);
    }
}
