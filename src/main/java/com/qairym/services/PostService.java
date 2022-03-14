package com.qairym.services;

import java.util.List;

import com.google.common.collect.Lists;
import com.qairym.entities.Post;
import com.qairym.repositories.PostRepository;

import com.qairym.utils.PostUtil;
import com.qairym.utils.exceptions.post.PostNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Slf4j
public class PostService implements Servable<Post> {
    private PostRepository postRepository;

    @Override
    public Post save(Post payload) {
        log.info(payload.toString());
        if (payload.getAuthor().getUserId() == null) {
            throw new IllegalArgumentException("Inputs are null");
        }
        log.info("Saving post: {} to the database", payload);
        return this.postRepository.save(payload);
    }

    @Override
    public List<Post> findAll() {
        return Lists.newArrayList(
                this.postRepository.findAll()
        );
    }

    @Override
    public Post findById(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new PostNotFoundException("Post not found"));
    }

    @Override
    public Post update(Post payload) {
        Post current = this.findById(payload.getPostId());
        Post post = PostUtil.mergeInstances(current, payload);

        return this.postRepository.save(post);
    }

    @Override
    public boolean delete(Long id) {
        postRepository.deleteById(id);
        return true;
    }

}
