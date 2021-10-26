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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

        System.out.println("save.title = " + save.getTitle());
        System.out.println("save.content = " + save.getContent());


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

    @Test
    void Posts를_수정하면_수정시간이_갱신된다() {
        //미리 저장된 값을 하나 생성해둠("1", "2");
        Posts save = postsRepository.save(Posts.builder()
                .title("1")
                .content("2")
                .build());
        LocalDateTime beforeTime = save.getModifiedDate();
        System.out.println("beforeTime = " + beforeTime);

        postsService.update(save.getId(), PostsUpdateRequestDto.builder()
                .title("test")
                .content("test2")
                .build());

        List<Posts> result = postsRepository.findAll();
        LocalDateTime newTime = result.get(0).getModifiedDate();

        System.out.println("newTime = " + newTime);
        assertThat(newTime).isAfter(beforeTime);
    }

    @Test
    void postsService를통해서_삭제가된다() {
        //미리 저장된 값을 하나 생성해둠("1", "2");
        Posts save = postsRepository.save(Posts.builder()
                .title("1")
                .content("2")
                .build());
        //해당 save id로 삭제
        postsService.delete(save.getId());
        //삭제 후 조회 시
        List<Posts> result = postsRepository.findAll();
        //아무것도 없어야 함
        assertThat(result).hasSize(0);
    }

    @Test
    void id가_일치해야만_삭제가된다() {
        //미리 저장된 값을 하나 생성해둠("1", "2");
        Posts save = postsRepository.save(Posts.builder()
                .title("1")
                .content("2")
                .build());

        Posts deleteTarget = postsRepository.save(Posts.builder()
                .title("1")
                .content("2")
                .build());

        //해당 deleteTarget 삭제
        postsService.delete(deleteTarget.getId());
        //삭제 후 조회 시
        Optional<Posts> byId = postsRepository.findById(deleteTarget.getId());
        //Optional은 null을 대체하기 위해서 나옴. 값이 null일 수도 있으니까 널체크를 꼭 해야됨.
        //Posts << null이 아닌 무조건 있는 값이 넘어옴.

        //아무것도 없어야 함
        assertThat(byId.isPresent()).isFalse(); //isPresent = posts가 null이 아니고 값이 있는 상태야?
        //assertThat(byId.get().getId()).isEqualTo(deleteTarget.getId());

    }
}
