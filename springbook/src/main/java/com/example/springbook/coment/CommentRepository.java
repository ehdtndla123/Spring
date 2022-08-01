package com.example.springbook.coment;

import com.example.springbook.coment.Comment;
import com.example.springbook.post.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Integer> {
    Page<Comment> findAllByPost(Post post, Pageable pageable);
}
