package com.qairym.services;

import java.util.List;

import com.qairym.entities.Post;
import com.qairym.repositories.PostRepository;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PostService implements Servable<Post> {
    private PostRepository postRepository;

    @Override
    public Post save(Post payload) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Post> findAll() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Post findById(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Post update(Post payload) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean delete(Long id) {
        // TODO Auto-generated method stub
        return false;
    }
    
}
