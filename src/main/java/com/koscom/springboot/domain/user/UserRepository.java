package com.koscom.springboot.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    //email주소로 user를 찾아주는 기능입니다
    //없을 수도 있지만 있을 경우에는 이메일이 같은 건을 반환한다.
    Optional<User> findByEmail(String email); //메소드명을 보고 자동으로 쿼리를 만들어줌
}
