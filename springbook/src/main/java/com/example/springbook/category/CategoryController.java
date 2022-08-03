package com.example.springbook.category;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
@RequestMapping("/dev")
public class CategoryController {
    private final CategoryService  categoryService;

    @RequestMapping
    @ResponseBody
    public String test(@RequestParam("category") String name,@RequestParam("sort") String sort){
        return name+sort;
    }

    @GetMapping("/category/create")
    public String createCategory(String name){
        return "category_form";
    }

    @PostMapping("/category/create")
    public String createCategory(Model model, @RequestParam("category") String category){
        this.categoryService.createCategory(category);
        return "redirect:/";
    }

}
