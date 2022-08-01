package com.example.springbook.category;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public void createCategory(String name){
        Category category=new Category();
        category.setName(name);
        this.categoryRepository.save(category);
    }


}
