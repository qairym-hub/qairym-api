package com.qairym.services;

import java.util.List;
import java.util.NoSuchElementException;

import com.google.common.collect.Lists;
import com.qairym.entities.Comment;
import com.qairym.entities.Like;
import com.qairym.entities.Post;
import com.qairym.entities.User;
import com.qairym.repositories.LikeRepository;
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
    private final LikeRepository likeRepository;

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
        return Lists.newArrayList(
                this.userRepository.findAll()
        );
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("User not found " + id)
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

        if (findAllFollowers(following).contains(currentFollower))
            throw new IllegalArgumentException("You already followed to this account");

        currentFollower.getFollowing().add(currentFollowing);
        this.userRepository.save(currentFollower);
        return true;
    }

    public boolean unFollow(Long follower, Long following) {
        User currentFollower = this.findById(follower);
        User currentFollowing = this.findById(following);

        currentFollower.getFollowing().remove(currentFollowing);
        this.userRepository.save(currentFollower);
        return true;
    }

    ////////////////////////////////////////// Like
    public List<Like> findAllLikes(Long id) {
        return Lists.newArrayList(
            this.postRepository.findById(id).orElseThrow(
                    () -> new NoSuchElementException("Post not found")
            ).getLikes()
        );
    }

    public Like like(Long liker, Long post) {
        User currentLiker = this.findById(liker);
        Post currentPost = this.postRepository.findById(post).orElseThrow(
                () -> new NoSuchElementException("Post not found")
        );
        Like like = new Like(null, currentLiker, currentPost);

        if (this.likeRepository.existsByLikerAndPost(currentLiker, currentPost))
            throw new IllegalArgumentException("You already liked this post");

        return this.likeRepository.save(like);
    }

    public boolean unLike(Long liker, Long post) {
        User currentLiker = this.findById(liker);
        Post currentPost = this.postRepository.findById(post).orElseThrow(
                () -> new NoSuchElementException("Post not found")
        );
        Like currentLike = this.likeRepository.findByLikerAndPost(currentLiker, currentPost);

        this.likeRepository.delete(currentLike);
        return true;
    }

    ////////////////////////////////////////// Comment
    public List<Comment> findAllComments(Long id) {
        return Lists.newArrayList(

        );
    }

    public boolean comment(Long commenter, Long following) {

        return true;
    }

    public boolean deleteComment(Long commenter, Long following) {

        return true;
    }

}
