package com.third.gachigong.service;

import com.third.gachigong.dto.MemberDto;
import com.third.gachigong.entity.MemberEntity;
import com.third.gachigong.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    public void save(MemberDto memberDto) {
        //1.dto -> entity 변환 (Entity에 구현해둠)
        //2. repository의 save 메서드 호출
        MemberEntity memberEntity = MemberEntity.toMemberEntity(memberDto);
        memberRepository.save(memberEntity);
    }

    public MemberDto login(MemberDto memberDto) {
        Optional<MemberEntity> byMemberId = memberRepository.findByMemberId(memberDto.getMemberId());
        if (byMemberId.isPresent()) {
            //조회 결과 있다 (해당 아이디를 가진 회원 정보가 있다)
            MemberEntity memberEntity = byMemberId.get();
            if (memberEntity.getMemberPw().equals(memberDto.getMemberPw())) {
                //entity -> dto 변환
                MemberDto dto = MemberDto.toMemberDto(memberEntity);
                return dto;
            }else {
                return null;
            }
        } else {
            return null;
        }
    }

    public boolean isMemberIdExists(String memberId) {
        return memberRepository.existsByMemberId(memberId);
    }

    public List<MemberDto> findAll(){
        List<MemberEntity> memberEntityList = memberRepository.findAll();
        List<MemberDto> memberDtoList = new ArrayList<>();
        for (MemberEntity memberEntity : memberEntityList){
            memberDtoList.add(MemberDto.toMemberDto(memberEntity));
        }
        return memberDtoList;
    }

    public void deleteById(Long id) {
        memberRepository.deleteById(id);
    }

    public MemberEntity findById(Long memberId) {
        return memberRepository.findById(memberId).orElse(null);
    }

    public MemberEntity findByMemberId(String memberId) {
        return memberRepository.findByMemberId(memberId).orElse(null);
    }
}
