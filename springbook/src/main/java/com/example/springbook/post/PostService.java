package com.example.springbook.post;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;

    public List<Post> getList(){
        return this.postRepository.findAll();
    }

    public Post getPost(Integer id){
        Optional<Post> p=this.postRepository.findById(id);
        if(p.isPresent()){
            return p.get();
        }
        return p.get();
    }
}
