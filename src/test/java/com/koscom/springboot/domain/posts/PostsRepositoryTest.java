package com.koscom.springboot.domain.posts;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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
}
