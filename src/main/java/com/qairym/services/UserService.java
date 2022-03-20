package com.qairym.services;

import java.util.List;
import java.util.NoSuchElementException;

import com.google.common.collect.Lists;
import com.qairym.entities.Like;
import com.qairym.entities.Post;
import com.qairym.entities.User;
import com.qairym.entities.models.UserModel;
import com.qairym.repositories.PostRepository;
import com.qairym.repositories.UserRepository;

import com.qairym.utils.UserUtil;
import com.qairym.utils.annotations.TestingOnly;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Slf4j
public class UserService implements Servable<User> {
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Override
    public User save(User payload) {
        if (userRepository.existsByUsername(payload.getUsername()))
            throw new IllegalArgumentException("User already exists");

        if (payload.getUsername() == null || payload.getPassword() == null)
            throw new IllegalArgumentException("Inputs are null");

        log.info("Saving user: {} to the database", payload);
        return userRepository.save(payload);
    }

    @TestingOnly
    @Override
    public List<User> findAll() {
        return (List<User>) UserModel.toModels(Lists.newArrayList(
            this.userRepository.findAll()
        ));
    }

    @Override
    public User findById(Long id) {
        return UserModel.toModel(
            userRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("User not found")
            )
        );
    }

    public User findByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }

    @Override
    public User update(User payload) {
        User current = this.findById(payload.getUserId());
        User user = UserUtil.mergeInstances(current, payload);

        return this.userRepository.save(user);
    }

    @Override
    public boolean delete(Long id) {
        userRepository.deleteById(id);
        return true;
    }

    ////////////////////////////////////////// Follow
    public List<User> findAllFollowers(Long id) {
        return Lists.newArrayList(
                this.userRepository.findAllByFollowing(findById(id))
        );
    }

    public boolean follow(Long follower, Long following) {
        User currentFollower = this.findById(follower);
        User currentFollowing = this.findById(following);

        if (currentFollower.getFollowing().contains(currentFollowing))
            throw new IllegalArgumentException("You already followed to this account");

        currentFollower.getFollowing().add(currentFollowing);
        this.userRepository.save(currentFollower);
        return true;
    }

    public boolean unFollow(Long follower, Long following) {

        // если не убрать у обоих то не уберется подписка
        // потом надо обсудить
        User currentFollower = this.findById(follower);
        User currentFollowing = this.findById(following);

        currentFollower.getFollowing().remove(currentFollowing);
        currentFollowing.getFollowers().remove(currentFollower);
        this.userRepository.save(currentFollower);
        this.userRepository.save(currentFollowing);
        return true;
    }

    ////////////////////////////////////////// Like
    public boolean like(Long liker, Long post) {
        User currentLiker = this.findById(liker);
        Post currentPost = this.postRepository.findById(post).orElseThrow(
                () -> new NoSuchElementException("Post not found")
        );
        Like like = new Like(null, currentLiker, currentPost);

        currentPost.getLikes().add(like);
        this.postRepository.save(currentPost);
        log.info(this.postRepository.findById(post).toString());
        return true;
    }

    public boolean unLike(Long liker, Long post) {
        User currentLiker = this.findById(liker);
        Post currentPost = this.postRepository.findById(post).orElseThrow(
                () -> new NoSuchElementException("Post not found")
        );
        Like like = new Like(null, currentLiker, currentPost);

        currentPost.getLikes().remove(like);
        return true;
    }

}
