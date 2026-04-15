package org.example.pedidosback.users.controller;

import lombok.RequiredArgsConstructor;
import org.example.pedidosback.users.DTO.UserRequest;
import org.example.pedidosback.users.domain.User;
import org.example.pedidosback.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping("/register")
    private ResponseEntity<User> register(@RequestBody UserRequest userRequest) {

        return ResponseEntity.ok(service.register(userRequest));
    }
}
