package com.example.demo.persistence;

import com.example.demo.domain.post.PostLikeEntity;
import com.example.demo.domain.post.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLikeEntity, Long> {

    // --- 1. 좋아요 여부 확인 ---
    // 특정 유저(userId)가 특정 게시글(post)에 이미 좋아요를 눌렀는지 확인
    // 버튼의 색깔을 바꾸거나 "이미 좋아요를 누르셨습니다"
    boolean existsByUserIdAndPost(Long userId, PostEntity post);

    // --- 2. 좋아요 취소(삭제)를 위한 조회 ---
    // 이미 누른 좋아요를 취소하려면 DB에서 해당 '좋아요 데이터'를 찾아야 함.
    // 유저 번호와 게시글 정보로 딱 하나의 좋아요 기록을 골라냄.
    Optional<PostLikeEntity> findByUserIdAndPost(Long userId, PostEntity post);

    // --- 3. 게시글별 좋아요 개수 카운트 ---
    // 게시판 목록에서 "좋아요 [5]" 처럼 숫자를 표시할 때 사용
    long countByPost(PostEntity post);
}
