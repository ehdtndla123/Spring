package com.example.springbook.category;

import com.example.springbook.post.Post;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 10,unique = true)
    private String name;

    @OneToMany(mappedBy = "category")
    private List<Post> postList=new ArrayList<>();


}
