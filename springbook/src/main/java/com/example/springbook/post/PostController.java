package com.example.springbook.post;

import com.example.springbook.coment.Comment;
import com.example.springbook.coment.CommentForm;
import com.example.springbook.coment.CommentService;
import com.example.springbook.user.SiteUser;
import com.example.springbook.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/dev/post")
@Controller
public class PostController {

    private final PostService postService;
    private final UserService userService;
    private final CommentService commentService;


    @RequestMapping("/list")
    public String list(Model model,@RequestParam(value = "page",defaultValue = "0") int page
    ,@RequestParam(value = "kw", defaultValue = "") String kw){
        Page<Post> paging=this.postService.getList(page,kw);
        model.addAttribute("paging",paging);
        model.addAttribute("kw",kw);
        return "post_list";
    }
    /*
    @RequestMapping(value ="/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id, CommentForm commentForm){
        Post post=this.postService.getPost(id);
        model.addAttribute("post",post);
        return "post_detail";
    }
     */
    @RequestMapping("/detail/{id}")
    public String detail(Model model,@PathVariable("id") Integer id,@RequestParam(value="page",defaultValue = "0") int page,CommentForm commentForm,
                         @RequestParam(value="order",defaultValue = "desc") String order)
    {
        Post post=this.postService.getPost(id);
        Page<Comment> paging=this.commentService.getCommentList(page,post,order);
        model.addAttribute("post",post);
        model.addAttribute("paging",paging);
        model.addAttribute("order",order);
        this.postService.updateViewCount(post);
        return "post_detail";
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String postCreate(PostForm postForm){
        return "post_create_form";
    }
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    // public String postCreate(@RequestParam String subject,@RequestParam String content){
    public String postCreate(@Valid PostForm postForm, BindingResult bindingResult, Principal principal){
        SiteUser user=this.userService.getUser(principal.getName());
        if(bindingResult.hasErrors()){
            return "post_create_form";
        }
        this.postService.create(postForm.getSubject(),postForm.getContent(),user);
        return "redirect:/dev/post/list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String postModify(PostForm postForm,@PathVariable("id") Integer id,Principal principal){
        Post post=this.postService.getPost(id);
        if(!post.getAuthor().getUsername().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        postForm.setContent(post.getContent());
        postForm.setSubject(post.getSubject());
        return "post_create_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String postModify(@Valid PostForm postForm,BindingResult bindingResult,Principal principal,@PathVariable("id") Integer id){
        if(bindingResult.hasErrors()){
            return "post_create_form";
        }
        Post post=this.postService.getPost(id);
        if (!post.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        this.postService.modify(post,postForm.getSubject(),postForm.getContent());
        return String.format("redirect:/dev/post/detail/%s",id);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String postDelete(Principal principal,@PathVariable("id") Integer id) {
        Post post = this.postService.getPost(id);
        if (!post.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        this.postService.delete(post);
        return "redirect:/";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/vote/{id}")
    public String vote(Principal principal,@PathVariable("id") Integer id){
        Post post=this.postService.getPost(id);
        SiteUser user=this.userService.getUser(principal.getName());
        this.postService.vote(post,user);
        return String.format("redirect:/dev/post/detail/%s",id);
    }

}
