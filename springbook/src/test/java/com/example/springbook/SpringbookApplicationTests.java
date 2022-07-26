package com.example.springbook;

import com.example.springbook.coment.Comment;
import com.example.springbook.coment.CommentRepository;
import com.example.springbook.post.Post;
import com.example.springbook.post.PostRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class SpringbookApplicationTests {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Test
    void contextLoads() {
        Optional<Post> optionalPost=this.postRepository.findById(1);
        assertTrue(optionalPost.isPresent());
        Post p=optionalPost.get();

        Comment comment=new Comment();
        comment.setContent("테스트입니다!!!");
        comment.setCreateDate(LocalDateTime.now());
        comment.setPost(p);

        this.commentRepository.save(comment);
    }

}
