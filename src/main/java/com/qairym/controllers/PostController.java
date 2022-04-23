package com.qairym.controllers;

import com.qairym.entities.Post;
import com.qairym.services.PostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/post")
@AllArgsConstructor
public class PostController {
    private final PostService postService;

    @ApiOperation(value = "Retrieve all posts of a certain user by userId from a database")
    @GetMapping("/find/{userId}")
    public ResponseEntity<?> findAllByUserId(@PathVariable String userId) throws NumberFormatException {
        return ResponseEntity.ok(
            this.postService.findAllByUser(Long.parseLong(userId))
        );
    }

    @ApiOperation(value = "Save a post into a database")
    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody Post payload) {
        return ResponseEntity.ok(
            this.postService.save(payload)
        );
    }
}
