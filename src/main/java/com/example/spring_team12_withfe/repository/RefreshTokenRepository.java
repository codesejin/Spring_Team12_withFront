package com.example.spring_team12_withfe.repository;


import com.example.spring_team12_withfe.domain.Member;
import com.example.spring_team12_withfe.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByMember(Member member);
}
