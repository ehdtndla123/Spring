package com.example.springbook.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post,Integer> {
    Page<Post> findAll(Pageable pageable);
    Page<Post> findAll(Specification<Post> spec, Pageable pageable);

    @Query("select " +
            "distinct p " +
            "from Post p " +
            "right outer join Category c on p.category=c " +
            "where " +
            "c.name like %:cg%")
    Page<Post> findAllByKeyword(@Param("cg") String cg,Pageable pageable);
}
