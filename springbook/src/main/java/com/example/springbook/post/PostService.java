package com.example.springbook.post;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    public void create(String subject,String content){
        Post post=new Post();
        post.setSubject(subject);
        post.setContent(content);
        post.setCreateDate(LocalDateTime.now());
        this.postRepository.save(post);
    }
}
