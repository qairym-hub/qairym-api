package com.qairym.controllers;

import com.qairym.entities.User;
import com.qairym.services.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserController {
    private UserService userService;

    @GetMapping("/")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(
                userService.findAll()
        );
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody User payload) {
        try {
            return ResponseEntity.ok(
                    userService.save(payload)
            );
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(
                    e.getMessage()
            );
        }
    }

}
