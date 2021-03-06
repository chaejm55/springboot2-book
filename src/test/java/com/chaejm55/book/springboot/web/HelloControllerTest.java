package com.chaejm55.book.springboot.web;

import com.chaejm55.book.springboot.config.auth.SecurityConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.is;

@RunWith(SpringRunner.class) // 스프링부트와 JUnit 사이를 연결함
@WebMvcTest(controllers = HelloController.class,
excludeFilters = { // CustomOAuth2UserService를 못 읽으므로 SecurityConfig도 읽지 않게 처리
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
    }
) // Web에 집중할 수 있는 어노테이션, 컨트롤러만 사용가능
public class HelloControllerTest {
    @Autowired  // 스프링이 관리하는 bean을 주입받음
    private MockMvc mvc; // 웹 API 테스트 시 사용, 이 클래스로 여러 API 테스트 가능

    @WithMockUser(roles="USER")
    @Test
    public void hello가_리턴된다() throws Exception{
        String hello = "hello";

        mvc.perform(get("/hello")) // MockMvc로 get 요청, 체이닝으로 여러 검증 이어서 선언
                .andExpect(status().isOk()) // Status 결과를 검증, 이 코드는 200 인지를 검증
                .andExpect(content().string(hello)); // 응답 본문 내용을 검증, 이 코드는  "hello"
    }

    @WithMockUser(roles="USER")
    @Test
    public void helloDto가_리턴된다() throws Exception{
        String name = "hello";
        int amount = 1000;

        mvc.perform(
                get("/hello/dto")
                    .param("name", name) // API 테스트 시 사용될 요청 파라미터, String만 허용(변환 해야 함)
                    .param("amount", String.valueOf(amount)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(name))) // JSON을 필드별로 검증하는 메서드, $로 필드명 명시
                .andExpect(jsonPath("$.amount", is(amount)));
    }
}
