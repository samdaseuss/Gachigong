package com.third.gachigong.repository;

import com.third.gachigong.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    //아이디로 회원 정보 조회 (select * from member_table where member_id=?)
    Optional<MemberEntity> findByMemberId(String memberId);
    boolean existsByMemberId(String memberId);
}
