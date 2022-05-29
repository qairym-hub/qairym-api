package com.qairym.dto;

import com.qairym.entities.Like;
import lombok.Data;

import java.util.List;

@Data
public class LikesDto {
    Integer likesCount;
    List<Like> likes;

    public LikesDto(Integer likesCount, List<Like> likes) {
        this.likesCount = likesCount;
        this.likes = likes;
    }
}
