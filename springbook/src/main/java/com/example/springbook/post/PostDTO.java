package com.example.springbook.post;


import com.example.springbook.coment.Comment;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostDTO {
    private String subject;
    private String content;
    private LocalDateTime localDateTime;
    private List<Comment> commentList;
}
