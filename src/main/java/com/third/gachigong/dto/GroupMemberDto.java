package com.third.gachigong.dto;

import com.third.gachigong.entity.GroupMemberEntity;
import com.third.gachigong.entity.MemberEntity;
import com.third.gachigong.entity.GroupEntity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GroupMemberDto {
    private long groupMemberId;
    private MemberEntity member;
    private GroupEntity group;

    public static GroupMemberDto toGroupMemberDto(GroupMemberEntity groupMemberEntity){
        GroupMemberDto groupMemberDto = new GroupMemberDto();
        groupMemberDto.setGroupMemberId(groupMemberEntity.getGroupMemberId());
        groupMemberDto.setMember(groupMemberEntity.getMember());
        groupMemberDto.setGroup(groupMemberEntity.getGroup());
        return groupMemberDto;
    }
}