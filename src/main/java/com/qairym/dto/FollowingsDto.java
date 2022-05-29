package com.qairym.dto;

import com.qairym.entities.user.User;
import lombok.Data;

import java.util.List;

@Data
public class FollowingsDto {

    Integer followingsCount;
    List<User> followings;

    public FollowingsDto(Integer followersCount, List<User> followers) {
        this.followingsCount = followersCount;
        this.followings = followers;
    }
}
