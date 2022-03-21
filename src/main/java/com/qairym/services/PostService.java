package com.qairym.services;

import java.util.List;
import java.util.NoSuchElementException;

import com.google.common.collect.Lists;
import com.qairym.entities.Post;
import com.qairym.entities.User;
import com.qairym.repositories.PostRepository;
import com.qairym.repositories.UserRepository;
import com.qairym.utils.PostUtil;
import com.qairym.utils.annotations.TestingOnly;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Slf4j
public class PostService implements Servable<Post> {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Override
    public Post save(Post payload) {
        if (payload.getAuthor().getUserId() == null)
            throw new IllegalArgumentException("Недопустимые значения полей.");
        
        log.info("Saving post: {} to the database", payload);
        return this.postRepository.save(payload);
    }

    @TestingOnly
    @Override
    public List<Post> findAll() {
        return Lists.newArrayList(
                this.postRepository.findAll()
        );
    }

    public List<Post> findAllByUser(Long userId) {
        User author = this.userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("Пользователь не найден."));

        return Lists.newArrayList(
            this.postRepository.findAllByAuthor(author)
        );
    }

    @Override
    public Post findById(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Пост не найден."));
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
