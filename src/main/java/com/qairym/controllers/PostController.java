package com.qairym.controllers;

import com.qairym.entities.Post;
import com.qairym.services.PostService;
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

    @GetMapping("/find/{userId}")
    public ResponseEntity<?> findAllByUserId(@PathVariable String userId) throws NumberFormatException {
        return ResponseEntity.ok(
            this.postService.findAllByUser(Long.parseLong(userId))
        );
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody Post payload) {
        try {
            return ResponseEntity.ok(
                    postService.save(payload)
            );
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(
                    e.getMessage()
            );
        }
    }
}
