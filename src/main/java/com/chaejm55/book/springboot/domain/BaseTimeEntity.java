package com.chaejm55.book.springboot.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass // 상속 시 필드도 칼럼으로 인식하게 함 
@EntityListeners(AuditingEntityListener.class) // Auditing 기능 포함
public abstract class BaseTimeEntity {
    
    @CreatedDate // Entity 생성 후 저장 시 시간 자동 저장
    private LocalDateTime createdDate;
    
    @LastModifiedDate // Entity 수정 시 시간 자동 저장
    private LocalDateTime modifiedDate;
}
