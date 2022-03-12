package com.qairym.repositories;

import com.qairym.entities.Post;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@Deprecated
public interface PostRepository extends CrudRepository<Post, Long> {
    
}
