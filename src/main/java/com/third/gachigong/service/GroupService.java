package com.third.gachigong.service;

import com.third.gachigong.entity.GroupEntity;
import com.third.gachigong.repository.GroupRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;

    // 모든 그룹 조회
    public List<GroupEntity> getAllGroups() {
        return groupRepository.findAll();
    }

    // 신규 그룹 생성
    public GroupEntity saveGroup(GroupEntity group) { return this.groupRepository.save(group); }

    // 참여 그룹 조회 ( 소유 포함 )
    public List<GroupEntity> getGroupsByUserId(long userId) {
        return groupRepository.getGroupsByUserId(userId);
    }

    // 소유 그룹 조회
    public List<GroupEntity> findGroupInfoByUserId(long userId) { return groupRepository.findGroupInfoByUserId(userId); }

    // 소유자 정보 업데이트
    @Transactional
    public void updateGroupOwner(long userId, long groupId) { groupRepository.updateGroupOwner( userId, groupId); }

    // 그룹 삭제
    @Transactional
    public void deleteGroup(long userId, long groupId) {
        groupRepository.deleteByGroupOwnerId(userId, groupId);
    }

    // 그룹 조회 ( by 그룹 아이디 )
    public GroupEntity findByGroupId(long groupId) {
        return groupRepository.findByGroupId(groupId);
    }

    // 그룹 조회 ( by 그룹 패스워드 )
    public GroupEntity findByGroupPw(Long groupId, String groupPw) {
        return groupRepository.findByGroupPw(groupId,groupPw);
    }

}
