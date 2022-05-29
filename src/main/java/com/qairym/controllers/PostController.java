package com.qairym.controllers;

import com.qairym.entities.post.Post;
import com.qairym.entities.post.PostPage;
import com.qairym.services.PostService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/post")
@AllArgsConstructor
public class PostController {
    private final PostService postService;

    @ApiOperation(value = "Retrieve all posts from a database")
    @GetMapping("/find")
    public ResponseEntity<List<Post>> findAll(@RequestParam(required = false) String search, PostPage postPage) {
        if (search != null) {
            return ResponseEntity.ok(
                    postService.findAllBySearch(search, postPage)
            );
        } else {
            return ResponseEntity.ok(
                    postService.findAll(postPage)
            );
        }
    }

    @ApiOperation(value = "Retrieve post by id from database")
    @GetMapping("/find/id/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return ResponseEntity.ok(
            postService.findById(id)
        );
    }

    @ApiOperation(value = "Retrieve all posts of a certain user by userId from a database")
    @GetMapping("/find/{userId}")
    public ResponseEntity<?> findAllByUserId(@PathVariable String userId, PostPage postPage) throws NumberFormatException {
        return ResponseEntity.ok(
            postService.findAllByUser(Long.parseLong(userId), postPage)
        );
    }

    @ApiOperation(value = "Save a post into a database")
    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody Post payload) {
        return ResponseEntity.ok(
            postService.save(payload)
        );
    }
}
