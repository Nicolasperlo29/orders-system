package org.example.pedidosback.users.controller;

import lombok.RequiredArgsConstructor;
import org.example.pedidosback.users.DTO.LoginRequest;
import org.example.pedidosback.users.DTO.UserRequest;
import org.example.pedidosback.users.domain.User;
import org.example.pedidosback.users.repository.UserRepository;
import org.example.pedidosback.users.service.UserService;
import org.example.pedidosback.users.service.implementation.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    private final AuthService authService;
    private final UserRepository userRepository;

    @PostMapping("/register")
    private ResponseEntity<User> register(@RequestBody UserRequest userRequest) {

        return ResponseEntity.ok(service.register(userRequest));
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody LoginRequest request) {
        String token = authService.login(request.getEmail(), request.getPassword());
        return Map.of("token", token);
    }

    @GetMapping("/all")
    public List<User> getUsers() {
        return userRepository.findAll();
    }
}
