package com.example.demo.domain.comment;
import com.example.demo.domain.post.PostEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@ToString(exclude = "post")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "comments")
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private PostEntity post;

    @NotBlank(message = "댓글내용은 필수입니다")
    @Size(max = 1000, message = "내용은 1000자이하여야 합니다")
    @Column(name = "content", columnDefinition = "TEXT", nullable = false)
    private String content;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Builder
    public CommentEntity(Long userId, PostEntity post, String content) {
        this.userId = userId;
        this.post = post;
        this.content = content;
    }

    // --- 비즈니스 로직 (댓글 수정) ---

    public CommentEntity updateContent(String content) {
        this.content = content;
        return this;
    }
}