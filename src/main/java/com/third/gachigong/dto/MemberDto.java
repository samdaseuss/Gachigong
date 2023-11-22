package com.third.gachigong.dto;

import com.third.gachigong.entity.MemberEntity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemberDto {
    private Long id;
    private String memberId;
    private String memberPw;
    private String memberName;

    public static MemberDto toMemberDto(MemberEntity memberEntity){
        MemberDto memberDto = new MemberDto();
        memberDto.setId(memberEntity.getId());
        memberDto.setMemberId(memberEntity.getMemberId());
        memberDto.setMemberPw(memberEntity.getMemberPw());
        memberDto.setMemberName(memberEntity.getMemberName());
        return memberDto;
    }
}
