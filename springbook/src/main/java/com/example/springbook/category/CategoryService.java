package com.example.springbook.category;

import com.example.springbook.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public Category getCategory(Integer id){
        Optional<Category> category=this.categoryRepository.findById(id);
        if(category.isPresent()){
            return category.get();
        }else {
            throw new DataNotFoundException("category not found");
        }
    }
    public List<Category> getCategoryList(){
        List<Category> categoryList=this.categoryRepository.findAll();
        return categoryList;
    }

}
