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

    @GetMapping("/find/id/{id}")
    public ResponseEntity<?> findById(@PathVariable String id) {
        return ResponseEntity.ok(
            this.userService.findById(Long.parseLong(id))
        );
    }

    @GetMapping("/find/username/{username}")
    public ResponseEntity<?> findByUsername(@PathVariable String username) {
        return ResponseEntity.ok(
            this.userService.findByUSername(username)
        );
    } 

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody User payload) {
        return ResponseEntity.ok(
            userService.save(payload)
        );
    }

}
