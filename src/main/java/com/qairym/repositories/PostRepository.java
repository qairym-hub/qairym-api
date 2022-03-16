package com.qairym.repositories;

import com.qairym.entities.Post;
import com.qairym.entities.User;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends CrudRepository<Post, Long> {

    Iterable<Post> findAllByAuthor(User author);
}
