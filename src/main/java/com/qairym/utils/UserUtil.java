package com.qairym.utils;

import com.qairym.entities.User;

public class UserUtil {

    public static User mergeInstances(User current, User incoming) {
        return User.builder()
                .userId(current.getUserId())
                .username(incoming.getUsername() == null ? current.getUsername() : incoming.getUsername())
                .password(incoming.getPassword() == null ? current.getPassword() : incoming.getPassword())
                .about(incoming.getAbout() == null ? current.getAbout() : incoming.getAbout())
                .location(incoming.getLocation() == null ? current.getLocation() : incoming.getLocation())
                .build();
    }

    public static boolean validCredentials(String username, String password) {
        return (username == null || username.isEmpty()) || (password == null || password.isEmpty());
    }
}
