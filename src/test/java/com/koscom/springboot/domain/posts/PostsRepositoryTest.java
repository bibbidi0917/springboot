package com.koscom.springboot.domain.posts;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PostsRepositoryTest {

    @Autowired
    PostsRepository postsRepository;


   @AfterEach //각 테스트가 끝날 때 마다 자동으로 해줌. 초기화되게 해주려고.
    void tearDown() {
        postsRepository.deleteAll();
    }

    @Test
    void 게시글저장_불러오기() {
        String title = "테스트 타이틀";
        String content = "테스트 본문";

        postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .build());
        
        List<Posts> result = postsRepository.findAll();

        System.out.println(result.get(0).getId()); //db가 만들어준 값
        System.out.println(result.get(0).getTitle());

        assertThat(result.get(0).getTitle()).isEqualTo(title);
        assertThat(result.get(0).getContent()).isEqualTo(content);
    }

    @Test
    void 게시글저장_불러오기2() {
        String title = "테스트 타이틀";
        String content = "테스트 본문";

        postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .build());

        List<Posts> result = postsRepository.findAll();

        System.out.println("size = " + result.size()); //db가 만들어준 값
        assertThat(result).hasSize(1);
    }

    @Test
    void 게시글저장_불러오기3() {
        String title = "테스트 타이틀";
        String content = "테스트 본문";

        postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .build());

        List<Posts> result = postsRepository.findAll();

        System.out.println("size = " + result.size()); //db가 만들어준 값
        assertThat(result).hasSize(1);
    }

    //LocalData : 일자 (2021.09.08)
    //LocalDataTime : 일시까지 (2021.09.08 10:55:01.9999)
    @Test
    public void 등록시간_수정시간이_저장된다 () {
//given
        LocalDateTime now = LocalDateTime.of(2019,6,4,0,0,0);
        postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .build());
        //지금 등록한 일시와 수정일시는 최소한 2019년보다는 뒤에 있을 거라는 확신
        //매일 수행할 때마다 테스트 일시가 변경되니, 특정 일시를 확정할 수 없는 테스트
        
        //when
        List<Posts> postsList = postsRepository.findAll();

        //then
        Posts posts = postsList.get(0);

        System.out.println(">>>>>>>>> createDate="+posts.getCreatedDate()+ ",modifiedDate ="+posts.getModifiedDate());

        assertThat(posts.getCreatedDate()).isAfter(now); //2019년보다는 생성일이 뒤에 있느냐
        assertThat(posts.getModifiedDate()).isAfter(now); //2019년보다는 수정일이 뒤에 있느냐
    }

}
