package com.third.gachigong.entity;

import com.third.gachigong.dto.GroupDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "study_group")
public class GroupEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String groupName;

    @Column
    private String groupIntro;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="group_owner")
    private MemberEntity groupOwner;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private List<GroupMemberEntity> groupMembers;


    public static GroupEntity toGroupEntity(GroupDto groupDto){
        GroupEntity groupEntity = new GroupEntity();
        groupEntity.setId(groupDto.getId());
        groupEntity.setGroupName(groupDto.getGroupName());
        groupEntity.setGroupIntro(groupDto.getGroupIntro());
        groupEntity.setGroupOwner(groupDto.getGroupOwner());
        groupEntity.setGroupMembers(groupDto.getGroupMember());
        return groupEntity;
    }

}
