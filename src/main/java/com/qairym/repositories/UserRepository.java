package com.qairym.repositories;

import com.qairym.entities.post.Post;
import com.qairym.entities.user.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByUserId(Long userId);

    Optional<User> findUserByUserId(Long userId);

    boolean existsByUsername(String username);

    List<User> findAllByFollowing(User following);

    List<User> findUsersByFollowersContaining(User follower);

}
