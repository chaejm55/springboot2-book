package com.chaejm55.book.springboot.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // JSON을 반환하는 컨트롤러
public class HelloController {

    @GetMapping("/hello") // GET 요청을 받는 API 생성
    public String hello() {
        return "hello";
    }
}
