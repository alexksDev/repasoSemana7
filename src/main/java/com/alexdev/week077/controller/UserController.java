package com.alexdev.week077.controller;

import com.alexdev.week077.dto.NewIdDTO;
import com.alexdev.week077.dto.RegisterUserDTO;
import com.alexdev.week077.entity.User;
import com.alexdev.week077.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    @PostMapping("/register")
    public ResponseEntity<NewIdDTO> register(@Valid @RequestBody RegisterUserDTO registerUserDTO) {
        User user = modelMapper.map(registerUserDTO, User.class);
        User savedUser = userService.register(user);
        return ResponseEntity.ok(new NewIdDTO(String.valueOf(savedUser.getId())));
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.findById(id);
        return ResponseEntity.ok(user);
    }
}
