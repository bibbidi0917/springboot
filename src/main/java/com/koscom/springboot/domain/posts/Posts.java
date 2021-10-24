package com.koscom.springboot.domain.posts;

import com.koscom.springboot.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor //디폴트 생성자
@Entity
public class Posts extends BaseTimeEntity {

    @Id //pk
    @GeneratedValue(strategy = GenerationType.IDENTITY) //pk 채번 방식 auto increment
    private Long id; //pk (auto increment, bigint)

    @Column(length = 500, nullable = false)
    private String title;

    @Column(length = 2000, nullable = false) //varchar 2000
    private String content;

    private String author;//아무것도 선언하지 않아도 varchar(255), nullable = true가 디폴트

    @Builder //lombok의 builder
    public Posts(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    //title과 content만 수정가능하다.
    //author 수정 가능하지 않다.
    //수정일자도 신규 생성된다.
    public void update(String title, String content){
        this.title = title;
        this.content = content;
    }
}
