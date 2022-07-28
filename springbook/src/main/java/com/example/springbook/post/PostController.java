package com.example.springbook.post;

import com.example.springbook.coment.CommentForm;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/dev/post")
@Controller
public class PostController {

    private final PostService postService;


    @RequestMapping("/list")
    public String list(Model model,@RequestParam(value = "page",defaultValue = "0") int page){
        Page<Post> paging=this.postService.getList(page);
        model.addAttribute("paging",paging);
        return "post_list";
    }
    @RequestMapping(value ="/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id, CommentForm commentForm){
        Post post=this.postService.getPost(id);
        model.addAttribute("post",post);
        return "post_detail";
    }

    @GetMapping("/create")
    public String postCreate(PostForm postForm){
        return "post_create_form";
    }
    @PostMapping("/create")
    // public String postCreate(@RequestParam String subject,@RequestParam String content){
    public String postCreate(@Valid PostForm postForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "post_create_form";
        }
        this.postService.create(postForm.getSubject(),postForm.getContent());
        return "redirect:/dev/post/list";
    }


}
