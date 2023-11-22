package com.example.studytime.entity;

import com.example.studytime.dto.GroupMemberDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "groupMember")
public class GroupMemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_member_id")
    private long groupMemberId;

    @ManyToOne
    @JoinColumn(name = "id")
    private MemberEntity member;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private GroupEntity group;


    public static GroupMemberEntity toGroupMemberEntity(GroupMemberDto groupMemberDto){
        GroupMemberEntity groupMemberEntity = new GroupMemberEntity();
        groupMemberEntity.setGroupMemberId(groupMemberDto.getGroupMemberId());
        groupMemberEntity.setMember(groupMemberDto.getMember());
        groupMemberEntity.setGroup(groupMemberDto.getGroup());
        return groupMemberEntity;
    }
}
