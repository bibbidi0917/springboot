package com.koscom.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

//JpaRepository 상속 받으면 CRUD가 자동 구현됨
public interface PostsRepository extends JpaRepository<Posts, Long> {//대상 entity, pk type

    //SQL이 아니라 JPQL (JPA)
    @Query("SELECT p FROM Posts p ORDER BY p.id DESC")
    List<Posts> findAllDesc();
}
