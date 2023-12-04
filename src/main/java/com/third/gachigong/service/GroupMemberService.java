package com.third.gachigong.service;

import com.third.gachigong.entity.GroupEntity;
import com.third.gachigong.entity.GroupMemberEntity;
import com.third.gachigong.entity.MemberEntity;
import com.third.gachigong.repository.GroupMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class GroupMemberService {
    @Autowired
    private final GroupMemberRepository groupMemberRepository;

    public boolean saveGroupMember(MemberEntity user, GroupEntity group, long userId, long groupId) {
        // 이미 가입된 멤버인지 확인
        GroupMemberEntity groupMember = groupMemberRepository.findGroupMemberByUserIdAndGroupId(user.getId(), group.getId());
        // 있다면 이미 가입된 멤버이므로 저장 성공 여부 false
        if(groupMember != null) {
            return false;
        } else {
            // 없다면 가입 진행 후 저장 성공 여부 true
            GroupMemberEntity newGroupMember = new GroupMemberEntity();
            newGroupMember.setMember(user);
            newGroupMember.setGroup(group);
            groupMemberRepository.save(newGroupMember);
            return true;
        }
    }


    public List<Object[]> findMemberDetailsByGroupId(long groupId) {
        return groupMemberRepository.findMemberDetailsByGroupId(groupId);
    }



    // group에 해당하는 모든 유저 정보 조회
    public List<GroupMemberEntity> getAllMembersByGroup(GroupEntity group) {
        return groupMemberRepository.findByGroup(group);
    }


}
