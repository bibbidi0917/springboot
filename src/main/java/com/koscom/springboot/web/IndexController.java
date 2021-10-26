package com.koscom.springboot.web;

import com.koscom.springboot.config.auth.dto.SessionUser;
import com.koscom.springboot.config.auth.login.LoginUser;
import com.koscom.springboot.service.PostsService;
import com.koscom.springboot.web.dto.posts.PostsResponseDto;
import com.koscom.springboot.web.dto.posts.PostsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user) {
        postsService.save(new PostsSaveRequestDto("test", "test", "test"));
        model.addAttribute("posts", postsService.findAllDesc());

         if (user != null) { // (2)
             model.addAttribute("user", user.getName());
         }

        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }

    //localhost:8080/posts/update/1 << 1번글의 수정화면으로 이동
    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);

        return "posts-update";
    }
}
