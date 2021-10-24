package com.koscom.springboot.service;

import com.koscom.springboot.domain.posts.Posts;
import com.koscom.springboot.domain.posts.PostsRepository;
import com.koscom.springboot.web.dto.posts.PostsListResponseDto;
import com.koscom.springboot.web.dto.posts.PostsResponseDto;
import com.koscom.springboot.web.dto.posts.PostsSaveRequestDto;
import com.koscom.springboot.web.dto.posts.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor //final로 선언된 필드들은 생성자 항목으로 자동 포함해서 생성자 생성
@Service //spring bean 등록 & service 클래스 선언
public class PostsService {
    private final PostsRepository postsRepository;
    //이렇게 final로 선언만 하면, 이 final 변수를 자동으로 인젝션 시켜주는 생성자가 만들어짐.

    //등록
    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        Posts posts = postsRepository.save(requestDto.toEntity());
        return posts.getId();
    }

    //수정
    @Transactional
    public Long update(Long id, PostsUpdateRequestDto dto) {
        // DB에서 가져온 값을 JPA 내부에서 캐시(1차 캐시)
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당사용자는 없습니다. id = " + id));

        //Dirty Checking : 처음 entity랑 끝났을 때 entity가 다르면 update 쿼리 날림.
        entity.update(dto.getTitle(), dto.getContent());

        return entity.getId();
    }

    //조회 - myBatis 혹은 querydsl
    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당사용자는 없습니다. id = " + id));
        return new PostsResponseDto(entity);
    }

    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc().stream() //List<Posts>로 오면 그걸 스트림으로 바꾸고
                .map(PostsListResponseDto::new) //post 하나하나를 PostsListResponseDto 로 바꿔서
                .collect(Collectors.toList()); //list로 만들겠다
    }

    @Transactional //row lock을 잡아서 하나만
    public void delete(Long id) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당사용자는 없습니다. id = " + id));
        postsRepository.delete(posts); // (1)
    }

}
