package com.example.studytime.service;

import com.example.studytime.dto.GroupDto;
import com.example.studytime.entity.GroupEntity;
import com.example.studytime.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GroupService {
    private final GroupRepository groupRepository;

    // 모든 그룹 조회
    public void getAllGroups() {
        return groupRepository.findAll();
    }

    // 신규 그룹 생성
    public void saveGroup(GroupEntity group) {
        this.groupRepository.save(group);
    }

    // 참여 그룹 조회 ( 소유 포함 )
    public List<GroupEntity> getGroupByUserId(long userId) {
        return groupRepository.getGroupsByUserId(userId);
    }

    // 소유 그룹 조회
    public List<Group> findGroupInfoByUserId(long userId) {
        return groupRepository.findGroupInfoByUserId(userId);
    }

}
