package com.chaejm55.book.springboot.web.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter  // 선언된 모든 필드에 getter 추가
@RequiredArgsConstructor // final 필드에만 생성자 추가
public class HelloResponseDto {
    private final String name;
    private final int amount;
}
