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
    private final UserService userService;

    @GetMapping("/find")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(
                userService.findAll()
        );
    }

    @GetMapping("/find")
    public ResponseEntity<?> findByUsername(@RequestParam String username) {
        return ResponseEntity.ok(
            this.userService.findByUSername(username)
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
