package com.chaejm55.book.springboot.web;

import com.chaejm55.book.springboot.web.dto.HelloResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController // JSON을 반환하는 컨트롤러
public class HelloController {

    @GetMapping("/hello") // GET 요청을 받는 API 생성
    public String hello() {
        return "hello";
    }

    @GetMapping("hello/dto")
    public HelloResponseDto helloDto(@RequestParam("name") String name, @RequestParam("amount") int amount) {
        // 외부에서 API로 넘긴 파라미터를 가져오는 어노테이션
        return new HelloResponseDto(name, amount);
    }
}
