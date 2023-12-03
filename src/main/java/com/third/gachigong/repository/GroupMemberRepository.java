package com.third.gachigong.repository;

import com.third.gachigong.entity.GroupEntity;
import com.third.gachigong.entity.GroupMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GroupMemberRepository extends JpaRepository<GroupMemberEntity, Long> {
    @Query("SELECT gm FROM GroupMemberEntity gm WHERE gm.member.id = :userId AND gm.group.id = :groupId")
    GroupMemberEntity findGroupMemberByUserIdAndGroupId(@Param("userId") Long userId, @Param("groupId") Long groupId);

    @Query("SELECT gm FROM GroupMemberEntity gm INNER JOIN gm.member m WHERE gm.group.id = :groupId")
    List<Object[]> findMemberDetailsByGroupId(@Param("groupId") Long groupId);

    List<GroupMemberEntity> findByGroup(GroupEntity group);
}
