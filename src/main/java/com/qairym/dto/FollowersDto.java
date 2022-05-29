package com.qairym.dto;

import com.qairym.entities.user.User;
import lombok.Data;

import java.util.List;

@Data
public class FollowersDto {
    Integer followersCount;
    List<User> followers;

    public FollowersDto(Integer followersCount, List<User> followers) {
        this.followersCount = followersCount;
        this.followers = followers;
    }
}
