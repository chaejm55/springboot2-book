package com.chaejm55.book.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

// Posts 클래스로 DB를 접근하게 해줌
public interface PostsRepository extends JpaRepository<Posts, Long>{ // 이러면 CRUD가 자동 생성 됨

    @Query("SELECT p FROM Posts p ORDER BY p.id DESC") // 가독성이 더 좋음, 사실 기본 메서드로도 가능
    List<Posts> findAllDesc();
}
