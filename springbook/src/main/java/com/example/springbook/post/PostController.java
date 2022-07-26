package com.example.springbook.post;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/dev")
@Controller
public class PostController {

    private final PostService postService;


    @RequestMapping("/post/list")
    public String list(Model model){
        List<Post> postList=this.postService.getList();
        model.addAttribute("postList",postList);
        return "post_list";
    }
}
