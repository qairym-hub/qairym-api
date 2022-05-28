package com.qairym.utils;

import com.qairym.entities.post.Post;

public class PostUtil {

    public static Post mergeInstances(Post current, Post incoming) {
        return Post.builder()
                .postId(current.getPostId())
                .title(current.getTitle() == null ? current.getTitle() : incoming.getTitle())
                .text(current.getText() == null ? current.getText() : incoming.getText())
                .build();
    }

    public static boolean validCredentials(String title, String text) {
        return (title == null || title.isEmpty()) || (text == null || text.isEmpty());
    }
}
