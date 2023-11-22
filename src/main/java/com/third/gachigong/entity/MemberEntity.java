package com.third.gachigong.entity;

import com.third.gachigong.dto.MemberDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "member")
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String memberId;

    @Column
    private String memberPw;

    @Column
    private String memberName;

    public static MemberEntity toMemberEntity(MemberDto memberDto){
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setMemberId(memberDto.getMemberId());
        memberEntity.setMemberPw(memberDto.getMemberPw());
        memberEntity.setMemberName(memberDto.getMemberName());
        return memberEntity;
    }
}
