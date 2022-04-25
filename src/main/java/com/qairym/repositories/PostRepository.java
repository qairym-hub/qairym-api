package com.qairym.repositories;

import com.qairym.entities.post.Post;
import com.qairym.entities.user.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends PagingAndSortingRepository<Post, Long> {

//    Iterable<Post> findAllByAuthor(User author);
    Page<Post> findAllByAuthor(User author, Pageable pageable);
}
