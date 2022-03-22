package com.qairym.repositories;

import com.qairym.entities.Like;
import com.qairym.entities.Post;
import com.qairym.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends CrudRepository<Like, Long> {
    boolean existsByLikerAndPost(User user, Post post);
    void deleteByLikerAndPost(User user, Post post);

    Like findByLikerAndPost(User user, Post post);
}