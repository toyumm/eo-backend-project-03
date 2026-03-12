package com.example.demo.persistence;
import com.example.demo.domain.user.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Slf4j
@Transactional
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("회원가입 시 중복 체크 기능이 정상 동작해야 한다.")
    void testExistsByMethods() {
        // given
        UserEntity user = UserEntity.builder()
                .username("testuser")
                .name("최서윤")
                .nickname("테스트닉네임")
                .email("test@example.com")
                .password("password123")
                .build();
        userRepository.save(user);

        // when & then
        log.info("중복 체크 테스트 시작");
        assertTrue(userRepository.existsByUsername("testuser"));
        assertTrue(userRepository.existsByNickname("테스트닉네임"));
        assertTrue(userRepository.existsByEmail("test@example.com"));

        log.info("중복 체크 결과: 모두 정상 (True)");
    }

    @Test
    @DisplayName("아이디로 유저를 찾을 수 있어야 한다. (로그인 로직)")
    void testFindByUsername() {
        // given
        String username = "loginUser";
        userRepository.save(UserEntity.builder()
                .username(username)
                .name("최서윤")
                .nickname("로그인유저")
                .email("login@test.com")
                .password("pw123")
                .build());

        // when
        Optional<UserEntity> foundUser = userRepository.findByUsername(username);

        // then
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getUsername()).isEqualTo(username);
        log.info("찾은 유저 닉네임: {}", foundUser.get().getNickname());
    }

    @Test
    @DisplayName("닉네임으로 유저 정보를 조회할 수 있어야 한다.")
    void testFindByNickname() {
        // given
        String nickname = "고수개발자";
        userRepository.save(UserEntity.builder()
                .username("dev_king")
                .name("최서윤")
                .nickname(nickname)
                .email("dev@test.com")
                .password("pw123")
                .build());

        // when
        Optional<UserEntity> result = userRepository.findByNickname(nickname);

        // then
        assertThat(result).isPresent();
        assertThat(result.get().getNickname()).isEqualTo(nickname);
        log.info("닉네임 '{}'로 조회 성공", nickname);
    }

    @Test
    @DisplayName("아이디와 이메일이 모두 일치하는 유저를 찾을 수 있어야 한다. (비밀번호 찾기)")
    void testFindByUsernameAndEmail() {
        // given
        String username = "findMe";
        String email = "find@test.com";
        userRepository.save(UserEntity.builder()
                .username(username)
                .name("최서윤")
                .nickname("찾기테스트")
                .email(email)
                .password("secret")
                .build());

        // when
        Optional<UserEntity> result = userRepository.findByUsernameAndEmail(username, email);

        // then
        assertThat(result).isPresent();
        assertThat(result.get().getUsername()).isEqualTo(username);
        assertThat(result.get().getEmail()).isEqualTo(email);
        log.info("아이디와 이메일 일치 확인 완료: {}", result.get().getUsername());
    }
}
