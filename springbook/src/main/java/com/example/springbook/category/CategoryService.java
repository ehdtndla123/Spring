package com.example.springbook.category;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public void createCategory(String name){
        Category category=new Category();
        category.setName(name);
        this.categoryRepository.save(category);
    }
    public Category getCategory(String name){
        Category category=this.categoryRepository.findAllByName(name);
        return category;
    }
    public List<Category> getCategoryList(){
        List<Category> categoryList=this.categoryRepository.findAll();
        return categoryList;
    }

}
