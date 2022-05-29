package com.qairym.services;

import com.qairym.entities.post.Post;
import com.qairym.entities.post.PostPage;
import com.qairym.entities.user.User;
import com.qairym.repositories.PostRepository;
import com.qairym.repositories.UserRepository;
import com.qairym.utils.PostUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

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

        this.userRepository.findById(payload.getAuthor().getUserId()).orElseThrow(
                () -> new NoSuchElementException("User not found")
        );

        log.info("Saving post: {} to the database", payload);
        return this.postRepository.save(payload);
    }

    public List<Post> findAll(PostPage postPage) {
        Sort sort = Sort.by(postPage.getSortDirection(), postPage.getSortBy());
        Pageable pageable = PageRequest.of(postPage.getPageNumber(), postPage.getPageSize(), sort);
        return postRepository.findAll(
                PageRequest.of(postPage.getPageNumber(), postPage.getPageSize(), sort)
        ).toList();
    }

    public List<Post> findAllBySearch(String search, PostPage postPage) {
        Sort sort = Sort.by(postPage.getSortDirection(), postPage.getSortBy());
        Pageable pageable = PageRequest.of(postPage.getPageNumber(), postPage.getPageSize(), sort);
        return postRepository.findPostsByTextContainsIgnoreCase(search,
                PageRequest.of(postPage.getPageNumber(), postPage.getPageSize(), sort)
        ).toList();
    }

    @Override
    public List<Post> findAll() {
        return null;
    }

    public List<Post> findAllByUser(Long userId, PostPage postPage) {
        User author = userRepository.findById(userId).orElseThrow(
                () -> new NoSuchElementException("Пользователь не найден.")
        );

        Sort sort = Sort.by(postPage.getSortDirection(), postPage.getSortBy());
        Pageable pageable = PageRequest.of(postPage.getPageNumber(), postPage.getPageSize(), sort);
        Page<Post> pageResult = postRepository.findByAuthor(author, pageable);
        return pageResult.toList();
    }

    @Override
    public Post findById(Long id) {
        return postRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Пост не найден.")
        );
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
