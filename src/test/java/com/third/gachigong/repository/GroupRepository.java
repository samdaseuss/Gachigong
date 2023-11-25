package com.third.gachigong.repository;public class GroupRepository {
}

    @Test
    @Transactional
    @Rollback(false) // 롤백을 막기 위해 사용 (옵션)
    void deleteByGroupOwnerIdTest() {

        GroupEntity group = new GroupEntity();
        MemberEntity member = new MemberEntity();
//
//        // 멤버
//        member.setId((long)2);
//        member.setMemberId("yunji");
//        member.setMemberName("오윤지");
//        member.setMemberPw("yunji1234");
//
//        // 그룹
//        group.setGroupName("테스트");
//        group.setGroupIntro("테스트 내용");
//        group.setGroupOwner(member);
//
//        // 그룹을 저장
//        groupRepository.save(group);
//
//        // ownerId에 해당하는 그룹 삭제
//        groupRepository.deleteByGroupOwnerId(group.getGroupOwner().getId());
//
//        // 해당 그룹이 더 이상 조회되지 않는지 확인
//        GroupEntity deletedGroup = groupRepository.findById(group.getId()).orElse(null);
//        assertNull(deletedGroup);
    }
}