package com.example.springbook.coment;

import com.example.springbook.post.Post;
import com.example.springbook.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@RequiredArgsConstructor
@Controller
@RequestMapping("/dev")
public class CommentController {

    private final PostService postService;
    private final CommentService commentService;

    @PostMapping("/comment/create/{id}")
    public String createComment(Model model, @PathVariable("id") Integer id, @Valid CommentForm commentForm, BindingResult bindingResult){
        Post post=this.postService.getPost(id);
        if(bindingResult.hasErrors()){
            model.addAttribute("post",post);
            return "post_detail";
        }
        this.commentService.create(post,commentForm.getContent());
        return String.format("redirect:/dev/post/detail/%s",id);
    }

}
