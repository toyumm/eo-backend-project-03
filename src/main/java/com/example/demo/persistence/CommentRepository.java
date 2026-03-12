package com.example.demo.persistence;

import com.example.demo.domain.comment.CommentEntity;
import com.example.demo.domain.post.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    // --- 게시글별 댓글 조회 ---
    /**
     * 특정 게시글(post)에 달린 모든 댓글을 조회
     * OrderByCreatedAtAsc를 사용하여 먼저 작성된 댓글부터 순서대로(최신글이 아래로) 정렬
     */
    List<CommentEntity> findByPostOrderByCreatedAtAsc(PostEntity post);

    // --- 게시글별 댓글 수 카운트 ---
    // 목록에서 "댓글 [5]"처럼 표시하기 위해 개수만
    long countByPost(PostEntity post);
}
