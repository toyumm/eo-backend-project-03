package com.example.demo.domain.user;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotBlank(message = "아이디는 필수입니다")
    @Size(min = 4, max = 50, message = "아이디는 4자 이상 50자 이하여야 합니다")
    @Column(name = "username", length = 50, nullable = false, unique = true)
    private String username;

    @NotBlank(message = "비밀번호는 필수입니다")
    @Column(name = "password", nullable = false)
    private String password;

    @NotBlank(message = "이름은 필수입니다")
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @NotBlank(message = "닉네임은 필수입니다")
    @Size(min = 2, max = 50, message = "닉네임은 2자 이상 50자 이하여야 합니다")
    @Column(name = "nickname", length = 50, nullable = false, unique = true)
    private String nickname;

    @NotBlank(message = "이메일은 필수입니다")
    @Column(name = "email", length = 100, nullable = false)
    private String email;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Builder
    public UserEntity(String username, String password, String name, String nickname, String email) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.email = email;
    }

    // --- 비즈니스 메서드 ---

    public UserEntity updatePassword(String password) {
        this.password = password;
        return this;
    }
}