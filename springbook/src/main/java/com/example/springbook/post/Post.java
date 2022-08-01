package com.example.springbook.post;

import com.example.springbook.category.Category;
import com.example.springbook.coment.Comment;
import com.example.springbook.user.SiteUser;
import jdk.jfr.Unsigned;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 200)
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    @OneToMany(mappedBy = "post",cascade = CascadeType.REMOVE)
    private List<Comment> commentList;

    @ManyToOne
    private SiteUser author;

    private LocalDateTime modifyDate;

    @Column(columnDefinition = "0")
    private Long viewCount;
    @ManyToMany
    Set<SiteUser> voter;

    @ManyToOne
    private Category category;
}
