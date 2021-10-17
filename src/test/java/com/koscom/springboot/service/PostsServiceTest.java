package com.koscom.springboot.service;

import com.koscom.springboot.domain.posts.Posts;
import com.koscom.springboot.domain.posts.PostsRepository;
import com.koscom.springboot.web.dto.posts.PostsSaveRequestDto;
import com.koscom.springboot.web.dto.posts.PostsUpdateRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PostsServiceTest {

    @Autowired
    PostsRepository postsRepository;

    @Autowired
    PostsService postsService;

    @AfterEach
    void tearDown() {
        postsRepository.deleteAll(); //JPA 테이블 보고 자식 테이블까지 삭제할 지 결정.
        postsRepository.deleteAllInBatch(); // delete from table. 전체 날림.
    }

    @Test
    void postsService를통해서_저장이된다() {
        String title = "test";
        String content = "test2";
        PostsSaveRequestDto dto = PostsSaveRequestDto.builder()
                .title(title)
                .content(content)
                .build();

        postsService.save(dto);

        System.out.println("save >>>>>>>>>>");
        List<Posts> result = postsRepository.findAll();

        System.out.println("findAll >>>>>>>>");
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTitle()).isEqualTo(title);
        assertThat(result.get(0).getContent()).isEqualTo(content);
    }

    @Test
    void postsService를통해서_수정이된다() {
        //미리 저장된 값을 하나 생성해둠("1", "2");
        Posts save = postsRepository.save(Posts.builder()
                .title("1")
                .content("2")
                .build());

        System.out.println("save.title = "+save.getTitle());
        System.out.println("save.content = "+save.getContent());


        String title = "test";
        String content = "test2";
        PostsUpdateRequestDto dto = PostsUpdateRequestDto.builder()
                .title(title)
                .content(content)
                .build();

        postsService.update(save.getId(), dto);

        List<Posts> result = postsRepository.findAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTitle()).isEqualTo(title);
        assertThat(result.get(0).getContent()).isEqualTo(content);
    }
}
