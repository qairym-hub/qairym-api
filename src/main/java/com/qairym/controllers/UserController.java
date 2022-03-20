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
            this.userService.findAll()
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
            this.userService.findByUsername(username)
        );
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody User payload) {
        return ResponseEntity.ok(
            this.userService.save(payload)
        );
    }

    ////////////////////////////////////////// Follow
    @GetMapping("/find/followers/{id}")
    public ResponseEntity<?> findFollowersById(@PathVariable String id) {
        return ResponseEntity.ok(
            this.userService.findAllFollowers(Long.parseLong(id))
        );
    }

    @PostMapping("/follow")
    public ResponseEntity<?> follow(@RequestParam Long follower, @RequestParam Long following) {
        return ResponseEntity.ok(
            this.userService.follow(follower, following)
        );
    }

    @PostMapping("/unfollow")
    public ResponseEntity<?> unFollow(@RequestParam Long follower, @RequestParam Long following) {
        return ResponseEntity.ok(
                this.userService.unFollow(follower, following)
        );
    }

    ////////////////////////////////////////// Like
    @PostMapping("/like")
    public ResponseEntity<?> like(@RequestParam String liker, @RequestParam String post) {
        return ResponseEntity.ok(
                this.userService.like(Long.parseLong(liker), Long.parseLong(post))
        );
    }

    @PostMapping("/unlike")
    public ResponseEntity<?> unLike(@RequestParam Long liker, @RequestParam Long post) {
        return ResponseEntity.ok(
                this.userService.unLike(liker, post)
        );
    }

}
