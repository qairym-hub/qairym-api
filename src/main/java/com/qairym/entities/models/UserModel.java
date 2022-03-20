package com.qairym.entities.models;

import com.qairym.entities.City;
import com.qairym.entities.User;
import lombok.Builder;
import lombok.Data;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;

@Data
@Builder
public class UserModel {
    private Long userId;

    private String username;
    private String password;

    private String about;

    private Date createdAt;
    private City location;

    public static User toModelUser(User entity) {
        return User.builder()
                .userId(entity.getUserId())
                .username(entity.getUsername())
                .password(entity.getPassword())
                .about(entity.getAbout())
                .createdAt(entity.getCreatedAt())
                .location(entity.getLocation())
                .comments(null)
                .followers(null)
                .following(null)
                .posts(null)
                .build();
    }

    public static Collection<User> toModels(Collection<User> entities) {
        Collection<User> models = new ArrayList<>();
        for (User user: entities) {
            User model = toModelUser(user);
            models.add(model);
        }
        return models;
    }

    public static User toModel(User entity) {
        return User.builder()
                .userId(entity.getUserId())
                .username(entity.getUsername())
                .password(entity.getPassword())
                .about(entity.getAbout())
                .createdAt(entity.getCreatedAt())
                .location(entity.getLocation())
                .comments(null)
                .followers(toModels(entity.getFollowers()))
                .following(toModels(entity.getFollowing()))
                .posts(null)
                .build();
    }

}
