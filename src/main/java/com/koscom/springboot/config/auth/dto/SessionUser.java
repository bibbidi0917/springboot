package com.koscom.springboot.config.auth.dto;

import com.koscom.springboot.domain.user.User;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable { //serializable이 있어야 세션에 저장할 수 있음
    //일반 User 클래스에 serializable 을 붙이면 안 되는 이유는, List<> 이런 건 사이즈가 너무 커서 세션에 저장하기 힘들어짐. OOM.
    private String name;
    private String email;
    private String picture;

    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}