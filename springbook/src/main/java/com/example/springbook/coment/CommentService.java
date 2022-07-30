package com.example.springbook.coment;

import com.example.springbook.DataNotFoundException;
import com.example.springbook.post.Post;
import com.example.springbook.user.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;

    public Comment getComment(Integer id){
        Optional<Comment> comment=this.commentRepository.findById(id);
        if(comment.isPresent()){
            return comment.get();
        }else {
            throw new DataNotFoundException("answer not found");
        }
    }

    public Comment create(Post post, String content, SiteUser author){
        Comment comment=new Comment();
        comment.setCreateDate(LocalDateTime.now());
        comment.setContent(content);
        comment.setPost(post);
        comment.setAuthor(author);
        this.commentRepository.save(comment);
        return comment;
    }

    public void modify(Comment comment,String content){
        comment.setContent(content);
        comment.setModifyDate(LocalDateTime.now());
        this.commentRepository.save(comment);
    }

    public void delete(Comment comment){
        this.commentRepository.delete(comment);
    }

    public void vote(Comment comment,SiteUser siteUser){
        if(comment.getVoter().contains(siteUser)){
            comment.getVoter().remove(siteUser);
        }else{
            comment.getVoter().add(siteUser);
        }
        this.commentRepository.save(comment);
    }
}
