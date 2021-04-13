package com.chaejm55.book.springboot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration // WebMvcTest (hello test에서 사용)은 @Configuration 스캔 안 함
@EnableJpaAuditing // Application에서 뺀 부분 활성화
public class JpaConfig { // 따로 떼서 테스트 하기 위함
}
