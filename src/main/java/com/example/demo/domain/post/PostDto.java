package com.example.demo.domain.post;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
    private Long id;
    private Long userId;
    private String nickname;    // (화면에 표시용)
    private String title;
    private String content;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Integer likesCount;    // 좋아요 수

    public static PostDto from(PostEntity entity, String nickname) {
        if (entity == null) {
            throw new IllegalArgumentException("UserEntity cannot be null");
        }
        return PostDto.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .nickname(nickname) // 엔티티엔 없지만 화면엔 필요한 정보
                .title(entity.getTitle())
                .content(entity.getContent())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .likesCount(entity.getPostLikeList() != null ? entity.getPostLikeList().size() : 0)
                .build();
    }
}
