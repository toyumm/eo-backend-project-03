package com.example.demo.domain.user;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;            // 유저 고유 번호
    private String username;    // 아이디
    private String name;        // 이름
    private String nickname;    // 닉네임
    private String email;       // 이메일

    private LocalDateTime createdAt; // 가입일
    private LocalDateTime updatedAt; // 정보 수정일

    /**
     * 비밀번호(password)는 보안을 위해 제외하고 변환
     */
    public static UserDto from(UserEntity entity) {
        if (entity == null) {
            throw new IllegalArgumentException("UserEntity cannot be null");
        }
        return UserDto.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .name(entity.getName())
                .nickname(entity.getNickname())
                .email(entity.getEmail())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
