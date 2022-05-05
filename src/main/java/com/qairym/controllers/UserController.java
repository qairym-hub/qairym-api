package com.qairym.controllers;

import com.qairym.entities.user.User;
import com.qairym.entities.user.UserPage;
import com.qairym.services.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
@Api(value="onlinestore", description="Operations pertaining to users")
public class UserController {
    private final UserService userService;

    @ApiOperation(value = "Retrieve all users from a database")
    @GetMapping("/find")
    public ResponseEntity<?> findAll(UserPage userPage) {
        return ResponseEntity.ok(
            userService.findAll(userPage)
        );
    }

    @ApiOperation(value = "Retrieve a certain user by id from a database")
    @GetMapping("/find/id/{id}")
    public ResponseEntity<?> findById(@PathVariable String id) {
        return ResponseEntity.ok(
            userService.findById(Long.parseLong(id))
        );
    }

    @ApiOperation(value = "Retrieve a certain user by username from a database")
    @GetMapping("/find/username/{username}")
    public ResponseEntity<?> findByUsername(@PathVariable String username) {
        return ResponseEntity.ok(
            userService.findByUsername(username)
        );
    }

    @ApiOperation(value = "Save a user into a database")
    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody User payload) {
        return ResponseEntity.ok(
            userService.save(payload)
        );
    }

    // Follow
    @ApiOperation(value = "Retrieve followers of a certain user by id from a database")
    @GetMapping("/find/followers/{id}")
    public ResponseEntity<?> findFollowersById(@PathVariable String id) {
        return ResponseEntity.ok(
            userService.findAllFollowers(Long.parseLong(id))
        );
    }

    @ApiOperation(value = "Follow a certain user by id")
    @PostMapping("/follow")
    public ResponseEntity<?> follow(@RequestParam Long follower, @RequestParam Long following) {
        return ResponseEntity.ok(
            userService.follow(follower, following)
        );
    }

    @ApiOperation(value = "Unfollow a certain user by id")
    @PostMapping("/unfollow")
    public ResponseEntity<?> unFollow(@RequestParam Long follower, @RequestParam Long following) {
        return ResponseEntity.ok(
            userService.unFollow(follower, following)
        );
    }

    // Like
    @ApiOperation(value = "Retrieve likes of a certain post by postId")
    @GetMapping("/find/likes/{id}")
    public ResponseEntity<?> findLikesByPostId(@PathVariable String id) {
        return ResponseEntity.ok(
            userService.findAllLikes(Long.parseLong(id))
        );
    }

    @ApiOperation(value = "Like a certain post")
    @PostMapping("/like")
    public ResponseEntity<?> like(@RequestParam String liker, @RequestParam String post) {
        return ResponseEntity.ok(
            userService.like(Long.parseLong(liker), Long.parseLong(post))
        );
    }

    @ApiOperation(value = "Unlike a certain liked post")
    @PostMapping("/unlike")
    public ResponseEntity<?> unLike(@RequestParam Long liker, @RequestParam Long post) {
        return ResponseEntity.ok(
            userService.unLike(liker, post)
        );
    }

}
