package com.example.demo.domain.post;

import com.example.demo.domain.comment.CommentEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "posts")
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "post_title", length = 100, nullable = false)
    private String title;

    @Column(name = "content", columnDefinition = "TEXT", nullable = false)
    private String content;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @ToString.Exclude
    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<CommentEntity> commentEntityList = new ArrayList<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<PostLikeEntity> postLikeList = new ArrayList<>();


    @Builder
    public PostEntity(Long userId, String title, String content) {
        this.userId = userId;
        this.title = title;
        this.content = content;
    }
    // --- 비즈니스 메서드 ---

    public PostEntity updateTitle(String title) {
        this.title = title;
        return this;
    }

    public PostEntity updateContent(String content) {
        this.content = content;
        return this;
    }
}