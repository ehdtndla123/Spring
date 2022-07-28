package com.example.springbook.coment;

import com.example.springbook.post.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;

    public void create(Post post,String content){
        Comment comment=new Comment();
        comment.setCreateDate(LocalDateTime.now());
        comment.setContent(content);
        comment.setPost(post);
        this.commentRepository.save(comment);
    }
}
