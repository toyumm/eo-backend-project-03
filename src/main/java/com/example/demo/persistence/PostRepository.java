package com.example.demo.persistence;

import com.example.demo.domain.post.PostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PostRepository extends JpaRepository<PostEntity, Long> {

    // --- 1. 기본 목록 조회 ---
    // 전체 게시글 최신순 정렬
    List<PostEntity> findAllByOrderByCreatedAtDesc();

    // --- 2. 닉네임 검색 지원 ---
    // 특정 유저(ID)가 작성한 글 목록 최신순 조회
    List<PostEntity> findByUserIdOrderByCreatedAtDesc(Long userId);

    // --- 3. 제목 및 내용 검색 ---

    /**
     * 제목에 특정 키워드가 포함된 글 검색 (최신순)
     */
    List<PostEntity> findByTitleContainingOrderByCreatedAtDesc(String title);

    /**
     * 내용에 특정 키워드가 포함된 글 검색 (최신순)
     */
    List<PostEntity> findByContentContainingOrderByCreatedAtDesc(String content);

    /**
     * 제목 또는 내용에 키워드가 포함된 글 검색 (최신순)
     */
    List<PostEntity> findByTitleContainingOrContentContainingOrderByCreatedAtDesc(String title, String content);

    // --- 4. 페이징 처리 ---

    Page<PostEntity> findByTitleContaining(String title, Pageable pageable);
}