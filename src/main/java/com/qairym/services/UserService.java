package com.qairym.services;

import com.google.common.collect.Lists;
import com.qairym.entities.Comment;
import com.qairym.entities.Like;
import com.qairym.entities.Role;
import com.qairym.entities.post.Post;
import com.qairym.entities.user.User;
import com.qairym.entities.user.UserPage;
import com.qairym.repositories.LikeRepository;
import com.qairym.repositories.PostRepository;
import com.qairym.repositories.RoleRepository;
import com.qairym.repositories.UserRepository;
import com.qairym.utils.UserUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
@Slf4j
public class UserService implements Servable<User>, UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final PostRepository postRepository;
    private final LikeRepository likeRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new NoSuchElementException("User this user not found: " + username)
        );
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }

    @Override
    public User save(User payload) {
        if (userRepository.existsByUsername(payload.getUsername())){
            throw new IllegalArgumentException("Пользователь с таким именем уже существует.");
        }

        if (payload.getUsername() == null || payload.getPassword() == null) {
            throw new IllegalArgumentException("Недопустимые значения полей.");
        }

        log.info("Saving user: {} to the database", payload);
        payload.setPassword(passwordEncoder.encode(payload.getPassword()));
        userRepository.save(payload);
        addRoleToUser(payload.getUsername(), "ROLE_USER");
        return userRepository.save(payload);
    }

    public Role saveRole(Role role) {
        log.info("Saving role {} to the database", role.getName());
        return roleRepository.save(role);
    }

    public void addRoleToUser(String username, String roleName) {
        log.info("Adding role {} to user {}", roleName, username);
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new NoSuchElementException("User not found")
        );
        Role role = new Role(1L, "ROLE_USER");
        user.getRoles().add(role);
        userRepository.save(user);
    }

    public List<User> findAll(UserPage userPage) {
        Sort sort = Sort.by(userPage.getSortDirection(), userPage.getSortBy());
        Pageable pageable = PageRequest.of(userPage.getPageNumber(), userPage.getPageSize(), sort);
        Page<User> pageResult = userRepository.findAll(pageable);
        return pageResult.toList();
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public User findById(Long id) {
        return userRepository.findUserByUserId(id).orElseThrow(
                () -> new NoSuchElementException("Пользователь не найден.")
        );
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new NoSuchElementException("User not found")
        );
    }

    @Override
    public User update(User payload) {
        User current = this.findById(payload.getUserId());
        User user = UserUtil.mergeInstances(current, payload);

        return userRepository.save(user);
    }

    @Override
    public boolean delete(Long id) {
        userRepository.deleteById(id);
        return true;
    }

    // Follow
    public List<User> findAllFollowers(Long id) {
        return Lists.newArrayList(
                userRepository.findAllByFollowing(findById(id))
        );
    }

    public Integer findNumberOfFollowers(Long id) {
        return userRepository.findAllByFollowing(findById(id)).size();
    }

    public boolean follow(Long follower, Long following) {
        User currentFollower = this.findById(follower);
        User currentFollowing = this.findById(following);

        if (findAllFollowers(following).contains(currentFollower)) {
            throw new IllegalArgumentException("You already followed to this account");
        }

        currentFollower.getFollowing().add(currentFollowing);
        userRepository.save(currentFollower);
        return true;
    }

    public boolean unFollow(Long follower, Long following) {
        User currentFollower = this.findById(follower);
        User currentFollowing = this.findById(following);

        currentFollower.getFollowing().remove(currentFollowing);
        userRepository.save(currentFollower);
        return true;
    }

    // Like
    public List<Like> findAllLikes(Long id) {
        return Lists.newArrayList(
                postRepository.findById(id).orElseThrow(
                        () -> new NoSuchElementException("Post not found")
                ).getLikes()
        );
    }

    public Integer findNumberOfLikes(Long id) {
        return postRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Post not found")
        ).getLikes().size();
    }
    public Like like(Long liker, Long post) {
        User currentLiker = this.findById(liker);
        Post currentPost = postRepository.findById(post).orElseThrow(
                () -> new NoSuchElementException("Post not found")
        );
        Like like = new Like(null, currentLiker, currentPost);

        if (this.likeRepository.existsByLikerAndPost(currentLiker, currentPost)) {
            throw new IllegalArgumentException("You already liked this post");
        }

        return this.likeRepository.save(like);
    }

    public boolean unLike(Long liker, Long post) {
        User currentLiker = this.findById(liker);
        Post currentPost = postRepository.findById(post).orElseThrow(
                () -> new NoSuchElementException("Post not found")
        );
        Like currentLike = likeRepository.findByLikerAndPost(currentLiker, currentPost);

        likeRepository.delete(currentLike);
        return true;
    }

    // Comment
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
