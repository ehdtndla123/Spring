package com.example.springbook.post;

import com.example.springbook.category.Category;
import com.example.springbook.category.CategoryService;
import com.example.springbook.coment.Comment;
import com.example.springbook.user.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;

    private final CategoryService categoryService;
    public Page<Post> getList(int page,String kw,String category,String order){
        List<Sort.Order> list=new ArrayList<>();
        if(order.equals("vote")){
            list.add(Sort.Order.desc("voteCount"));
        }else if(order.equals("note")){
            list.add(Sort.Order.desc("commentCount"));
        }else if(order.equals("view")){
            list.add(Sort.Order.desc("viewCount"));
        }else{
            list.add(Sort.Order.desc("createDate"));
        }
        Pageable pageable= PageRequest.of(page,10,Sort.by(list));
        Specification<Post> spec=search(kw,category);
        return this.postRepository.findAll(spec,pageable);
    }

    public Post getPost(Integer id){
        Optional<Post> p=this.postRepository.findById(id);
        if(p.isPresent()){
            return p.get();
        }
        return p.get();
    }

    public void create(String subject, String content, SiteUser author,Integer categoryId){
        //카테고리 분류 받아와야함
        Category newCategory=this.categoryService.getCategory(categoryId);
        Post post=new Post();
        post.setSubject(subject);
        post.setContent(content);
        post.setCreateDate(LocalDateTime.now());
        post.setAuthor(author);
        post.setViewCount(0L);
        post.setCategory(newCategory);
        newCategory.getPostList().add(post);
        this.postRepository.save(post);
    }
    public void modify(Post post,String subject,String content){
        post.setSubject(subject);
        post.setContent(content);
        post.setModifyDate(LocalDateTime.now());
        this.postRepository.save(post);

    }
    public void delete(Post post){
        this.postRepository.delete(post);
    }

    public void vote(Post post,SiteUser siteUser){
        if(post.getVoter().contains(siteUser)){
            post.getVoter().remove(siteUser);
        }else {
            post.getVoter().add(siteUser);
        }
        this.postRepository.save(post);
    }
    private Specification<Post> search(String kw,String category) {
        return new Specification<>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<Post> p, CriteriaQuery<?> query, CriteriaBuilder cb) {
                query.distinct(true);  // 중복을 제거
                Join<Category,Post> c=p.join("category",JoinType.LEFT);
                Join<Post, SiteUser> u1 = p.join("author", JoinType.LEFT);
                Join<Post, Comment> a = p.join("commentList", JoinType.LEFT);
                Join<Comment, SiteUser> u2 = a.join("author", JoinType.LEFT);
                return cb.and(cb.like(c.get("name"),category),

                        cb.or(cb.like(p.get("subject"), "%" + kw + "%"), // 제목
                        cb.like(p.get("content"), "%" + kw + "%"),      // 내용
                        cb.like(u1.get("username"), "%" + kw + "%"),    // 질문 작성자
                        cb.like(a.get("content"), "%" + kw + "%"),      // 답변 내용
                        cb.like(u2.get("username"), "%" + kw + "%")));// 답변 작성자

            }
        };
    }

    public void updateViewCount(Post post){
        Long viewPoint=post.getViewCount()+1L;
        post.setViewCount(viewPoint);
        this.postRepository.save(post);
    }
}
