package com.third.gachigong.repository;

import com.third.gachigong.entity.GroupEntity;
import com.third.gachigong.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GroupRepository extends JpaRepository<GroupEntity, Long> {

    @Query("SELECT g FROM GroupEntity g JOIN g.groupMembers gm WHERE gm.member.id = :userId")
    List<GroupEntity> getGroupsByUserId( @Param("userId") long userId );

    @Query("SELECT g FROM GroupEntity g JOIN g.groupOwner go WHERE go.id = :userId")
    List<GroupEntity> findGroupInfoByUserId(@Param("userId") long userId );

}