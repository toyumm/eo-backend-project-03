package com.example.demo.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import com.example.demo.domain.user.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    // --- 1. 회원가입 (중복 체크) ---
    // 가입 시 입력한 정보가 이미 DB에 있는지 확인하여 중복 가입을 방지
    boolean existsByUsername(String username);
    boolean existsByNickname(String nickname);
    boolean existsByEmail(String email);

    // --- 2. 로그인 ---
    // 사용자가 입력한 아이디로 유저 정보를 가져와 비밀번호를 대조
    Optional<UserEntity> findByUsername(String username);

    // --- 3. 닉네임으로 검색 ---
    // 게시판에서 특정 닉네임이 쓴 글을 찾기 위해, 해당 닉네임의 유저 정보를 조회
    Optional<UserEntity> findByNickname(String nickname);

    // --- 4. 비밀번호 찾기 ---
    // "아이디"와 "이메일"이 모두 일치하는 유저가 있는지 확인하여 본인임을 인증
    // 이 정보가 일치해야만 비밀번호 재설정 페이지로 넘어가는 로직
    Optional<UserEntity> findByUsernameAndEmail(String username, String email);
}