package com.qairym.services;

import java.util.List;

import com.google.common.collect.Lists;
import com.qairym.entities.User;
import com.qairym.repositories.UserRepository;

import com.qairym.utils.UserUtil;
import com.qairym.utils.annotations.TestingOnly;
import com.qairym.utils.exceptions.user.UserAlreadyExistsException;
import com.qairym.utils.exceptions.user.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Slf4j
public class UserService implements Servable<User> {
    private final UserRepository userRepository;

    @Override
    public User save(User payload) {
        if (userRepository.existsByUsername(payload.getUsername()))
            throw new UserAlreadyExistsException("User already exists");
    
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
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    public User findByUSername(String username) {
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

}
