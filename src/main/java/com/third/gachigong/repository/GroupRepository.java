package com.third.gachigong.repository;

import jakarta.transaction.Transactional;
import com.third.gachigong.entity.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GroupRepository extends JpaRepository<GroupEntity, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE GroupEntity g SET g.groupOwner.id = :userId WHERE g.id = :groupId")
    void updateGroupOwner(@Param("userId") long userId, @Param("groupId") long groupId);

    @Query("SELECT g FROM GroupEntity g JOIN g.groupMembers gm WHERE gm.member.id = :userId")
    List<GroupEntity> getGroupsByUserId( @Param("userId") long userId );

    @Query("SELECT g FROM GroupEntity g JOIN g.groupOwner go WHERE go.id = :userId")
    List<GroupEntity> findGroupInfoByUserId(@Param("userId") long userId );

    @Modifying
    @Query("DELETE FROM GroupEntity g WHERE g.groupOwner.id = :userId AND g.id = :groupId")
    void deleteByGroupOwnerId(@Param("userId") Long userId, @Param("groupId") Long groupId);
}