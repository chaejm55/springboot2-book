package com.chaejm55.book.springboot.domain.posts;

import com.chaejm55.book.springboot.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

// Entity 클래스에서는 Setter 생성 안 함, 값 변경을 코드 상으로 알기 정말 복잡함
@Getter // 클래스 내 getter 자동 추가
@NoArgsConstructor // 기본 생성자 자동 추가
@Entity // 테이블과 링크될 클래스임, 언더 케이스로 매칭
public class Posts extends BaseTimeEntity {
    @Id // 해당 테이블의 PK(Primary Key: 기본키, 테이블의 유일한 값) 필드
    @GeneratedValue(strategy = GenerationType.IDENTITY) // PK의 생성규칙을 나타냄, increment로 사용
    private long id;

    @Column(length = 500, nullable = false) // 테이블의 칼럼이 됨, 추가 옵션 필요 시 사용: 타입이나 사이즈 변경
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    @Builder // 해당 클래스의 빌더 패턴 클래스 생성, 생성자에 포함된 필드만 + 어느 필드에 어떤 값을 채워야 하는지 알기 쉬움
    public Posts(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
