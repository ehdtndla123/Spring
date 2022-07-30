package com.example.springbook.post;

import com.example.springbook.user.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;

    public Page<Post> getList(int page){
        List<Sort.Order> list=new ArrayList<>();
        list.add(Sort.Order.desc("createDate"));
        Pageable pageable= PageRequest.of(page,10,Sort.by(list));
        return this.postRepository.findAll(pageable);
    }

    public Post getPost(Integer id){
        Optional<Post> p=this.postRepository.findById(id);
        if(p.isPresent()){
            return p.get();
        }
        return p.get();
    }

    public void create(String subject, String content, SiteUser author){
        Post post=new Post();
        post.setSubject(subject);
        post.setContent(content);
        post.setCreateDate(LocalDateTime.now());
        post.setAuthor(author);
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
}
