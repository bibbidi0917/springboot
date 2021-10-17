package com.koscom.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;

//JpaRepository 상속 받으면 CRUD가 자동 구현됨
public interface PostsRepository extends JpaRepository<Posts, Long> {//대상 entity, pk type

}
