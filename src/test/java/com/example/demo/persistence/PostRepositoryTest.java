package com.example.demo.persistence;
import com.example.demo.domain.post.PostEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Slf4j
@Transactional
public class PostRepositoryTest {
    @Autowired
    private PostRepository postRepository;

    private final Long TEST_USER_ID = 1L;

    @Test
    @DisplayName("Repository 연결 및 생성 테스트")
    public void testCreate() {
        // given
        String title = "테스트 제목";
        PostEntity post = PostEntity.builder()
                .userId(TEST_USER_ID)
                .title(title)
                .content("테스트 내용입니다.")
                .build();

        // when
        PostEntity savedPost = postRepository.save(post);

        // then
        assertNotNull(savedPost.getId());
        log.info("저장된 게시글 ID: {}", savedPost.getId());
        assertThat(savedPost.getTitle()).isEqualTo(title);
    }

    @Test
    @DisplayName("제목 키워드 검색 테스트")
    public void testFindByTitleContaining() {
        // given
        postRepository.save(PostEntity.builder().userId(TEST_USER_ID).title("사과 맛있어요").content("내용").build());
        postRepository.save(PostEntity.builder().userId(TEST_USER_ID).title("바나나 길어요").content("내용").build());

        // when
        String keyword = "사과";
        List<PostEntity> result = postRepository.findByTitleContainingOrderByCreatedAtDesc(keyword);

        // then
        log.info("제목에 '{}'가 포함된 게시글 수: {}", keyword, result.size());
        assertThat(result).isNotEmpty();
        assertThat(result.get(0).getTitle()).contains(keyword);
    }

    @Test
    @DisplayName("제목 또는 내용 키워드 통합 검색 테스트")
    public void testFindByTitleOrContent() {
        // given
        postRepository.save(PostEntity.builder().userId(TEST_USER_ID).title("JPA 공부").content("너무 재밌다").build());
        postRepository.save(PostEntity.builder().userId(TEST_USER_ID).title("제목").content("JPA 최고").build());

        // when
        String keyword = "JPA";
        List<PostEntity> result = postRepository.findByTitleContainingOrContentContainingOrderByCreatedAtDesc(keyword, keyword);

        // then
        log.info("'{}' 키워드 통합 검색 결과 수: {}", keyword, result.size());
        assertThat(result.size()).isGreaterThanOrEqualTo(2);
    }

    @Test
    @DisplayName("특정 유저의 작성 글 최신순 조회 테스트")
    public void testFindByUserId() {
        // given
        postRepository.save(PostEntity.builder().userId(TEST_USER_ID).title("첫 번째 글").content("내용").build());
        postRepository.save(PostEntity.builder().userId(TEST_USER_ID).title("두 번째 글").content("내용").build());

        // when
        List<PostEntity> result = postRepository.findByUserIdOrderByCreatedAtDesc(TEST_USER_ID);

        // then
        log.info("유저번호 {}의 작성 게시글 수: {}", TEST_USER_ID, result.size());
        assertThat(result).isNotEmpty();
        // 최신순 정렬 확인 (두 번째 글이 첫 번째에 와야 함)
        assertThat(result.get(0).getTitle()).isEqualTo("두 번째 글");
    }
}
