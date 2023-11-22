package com.example.studytime.dto;

import com.example.studytime.entity.GroupMemberEntity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GroupMemberDto {
    private long groupMemberId;
    private Member member;
    private Group group;

    public static GroupMemberDto toGroupMemberDto(GroupMemberEntity groupMemberEntity){
        GroupMemberDto groupMemberDto = new GroupMemberDto();
        groupMemberDto.setGroupMemberId(GroupMemberEntity.getGroupMemberId());
        groupMemberDto.setMember(GroupMemberEntity.getMember());
        groupMemberDto.setGroup(GroupMemberEntity.getGroup());
        return groupMemberDto;
    }
}
