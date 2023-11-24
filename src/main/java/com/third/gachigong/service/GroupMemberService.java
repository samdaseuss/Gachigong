package com.third.gachigong.service;

import com.third.gachigong.dto.GroupMemberDto;
import com.third.gachigong.entity.GroupEntity;
import com.third.gachigong.entity.GroupMemberEntity;
import com.third.gachigong.entity.MemberEntity;
import com.third.gachigong.repository.GroupMemberRepository;
import com.third.gachigong.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class GroupMemberService {
    private final GroupMemberRepository groupMemberRepository;
    private final MemberRepository memberRepository;
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
}
