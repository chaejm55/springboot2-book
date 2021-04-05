package com.chaejm55.book.springboot.web;

import com.chaejm55.book.springboot.service.posts.PostsService;
import com.chaejm55.book.springboot.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;

    @GetMapping("/")
    public String index(Model model) { // 머스태치 스타터가 뒤의 파일 확장자 자동 지정
        model.addAttribute("posts", postsService.findAllDesc());
        return "index"; // index를 반환 하므로 ~~~/index.mustache임
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);

        return "posts-update";
    }
}
