package com.example.springbook.coment;

import com.example.springbook.post.Post;
import com.example.springbook.post.PostService;
import com.example.springbook.user.SiteUser;
import com.example.springbook.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.security.Principal;

@RequiredArgsConstructor
@Controller
@RequestMapping("/dev/comment")
public class CommentController {

    private final PostService postService;
    private final CommentService commentService;
    private final UserService userService;
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create/{id}")
    public String createComment(Model model, @PathVariable("id") Integer id, @Valid CommentForm commentForm, BindingResult bindingResult, Principal principal){
        Post post=this.postService.getPost(id);
        SiteUser user=this.userService.getUser(principal.getName());
        if(bindingResult.hasErrors()){
            model.addAttribute("post",post);
            return "post_detail";
        }
        Comment comment=this.commentService.create(post,commentForm.getContent(),user);
        return String.format("redirect:/dev/post/detail/%s#comment_%s",id,comment.getId());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String modifyComment(CommentForm commentForm,@PathVariable("id") Integer id,Principal principal){
        Comment comment=this.commentService.getComment(id);
        if(!comment.getAuthor().getUsername().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        commentForm.setContent(comment.getContent());
        return "comment_form";
    }
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String modifyComment(@Valid CommentForm commentForm,BindingResult bindingResult,@PathVariable("id") Integer id,Principal principal){
        if(bindingResult.hasErrors()){
            return "comment_form";
        }
        Comment comment=this.commentService.getComment(id);
        if(!comment.getAuthor().getUsername().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        this.commentService.modify(comment,commentForm.getContent());
        return String.format("redirect:/dev/post/detail/%s#comment_%s",comment.getPost().getId(),comment.getId());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String deleteComment(Principal principal,@PathVariable("id") Integer id){
        Comment comment=this.commentService.getComment(id);
        if(!comment.getAuthor().getUsername().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        this.commentService.delete(comment);
        return String.format("redirect:/dev/post/detail/%s",comment.getPost().getId());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/vote/{id}")
    public String vote(Principal principal,@PathVariable("id") Integer id){
        Comment comment=this.commentService.getComment(id);
        SiteUser siteUser=this.userService.getUser(principal.getName());
        this.commentService.vote(comment,siteUser);
        return String.format("redirect:/dev/post/detail/%s#comment_%s",comment.getPost().getId(),comment.getId());
    }
}
