package com.koscom.springboot.web;

import com.koscom.springboot.config.auth.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = HelloController.class, //) //controller만 테스트. 다른 건 읽지마. 그래서 다른 서비스 빈들을 인식을 못함
        excludeFilters = { //그래서 CustomOAuth2UserService를 안 읽게 하기 위해서 SecurityConfig 쪽은 제외하라고 하겠다
                 @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes= SecurityConfig.class)}
                 )
        public class HelloControllerTest {

    @Autowired
    MockMvc mvc; //임시 테스트 객체

    @WithMockUser(roles = "USER") //가짜로 인증된 사용자를 넣음. MockMVC가 있을 때만 쓸 수 있음
    @Test
    void hello주소로요청이오면_hello가_리턴된다() throws Exception{
        String expectResult = "hello";

        mvc.perform(get("/hello")) //여기서 쏴줌
                .andExpect(status().isOk()) //여기부터 검증. http status가 200.
                .andExpect(content().string(expectResult)); //body에는 expectResult가 올 것이다.
    }

    @WithMockUser(roles = "USER") //가짜로 인증된 사용자를 넣음
    @Test
    void helloDto가_리턴된다() throws Exception {//정상 케이스
        String name = "hello";
        int amount = 1000;

        mvc.perform(get("/hello/dto")
                .param("name", name)
                .param("amount", String.valueOf(amount)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(name))) //$는 json의 { 를 나타냄.
                .andExpect(jsonPath("$.amount", is(amount)));
    }

    @WithMockUser(roles = "USER") //가짜로 인증된 사용자를 넣음
    @Test
    void amount가없으면_응답코드가_400이된다() throws Exception {//예외 케이스
        String name = "hello";

        mvc.perform(get("/hello/dto")
                        .param("name", name))
                .andExpect(status().isBadRequest());
    }
}
