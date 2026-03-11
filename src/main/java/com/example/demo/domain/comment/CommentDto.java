package com.example.demo.domain.comment;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private Long id;
    private Long userId;
    private String nickname;    // (화면 표시용)
    private Long postId;
    private String content;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static CommentDto from(CommentEntity entity, String nickname) {
        if (entity == null) {
            throw new IllegalArgumentException("CommentEntity cannot be null");
        }

        return CommentDto.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .nickname(nickname)             // 외부에서 가져온 닉네임 주입
                .postId(entity.getPost().getId()) // 연결된 PostEntity에서 ID만 추출
                .content(entity.getContent())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
