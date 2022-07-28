package com.example.springbook;

import com.example.springbook.coment.Comment;
import com.example.springbook.coment.CommentRepository;
import com.example.springbook.post.Post;
import com.example.springbook.post.PostRepository;
import com.example.springbook.post.PostService;
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
    @Autowired
    private PostService postService;

    @Test
    void contextLoads() {
        for (int i = 1; i <= 300; i++) {
            String subject = String.format("테스트 데이터입니다:[%03d]", i);
            String content = "내용무";
            this.postService.create(subject, content);
        }
    }

}
