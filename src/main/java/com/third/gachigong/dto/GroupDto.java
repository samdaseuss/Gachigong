package com.third.gachigong.dto;

import com.third.gachigong.entity.GroupEntity;
import com.third.gachigong.entity.GroupMemberEntity;
import com.third.gachigong.entity.MemberEntity;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GroupDto {
    private long id;
    private String groupName;
    private String groupIntro;
    private String groupPassword;
    private MemberEntity groupOwner;
    private List<GroupMemberEntity> groupMember;

    public static GroupDto toGroupDto(GroupEntity groupEntity){
        GroupDto groupDto = new GroupDto();
        groupDto.setId(groupEntity.getId());
        groupDto.setGroupName(groupEntity.getGroupName());
        groupDto.setGroupIntro(groupEntity.getGroupIntro());
        groupDto.setGroupPassword(groupEntity.getGroupPassword());
        groupDto.setGroupOwner(groupEntity.getGroupOwner());
        groupDto.setGroupMember(groupEntity.getGroupMembers());
        return groupDto;
    }
}
