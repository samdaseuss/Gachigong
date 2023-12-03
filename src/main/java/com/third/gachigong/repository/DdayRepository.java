package com.third.gachigong.repository;

import com.third.gachigong.entity.DdayEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface DdayRepository extends JpaRepository<DdayEntity, Long > {

    @Query("SELECT d FROM DdayEntity d WHERE d.date = :date")
    List<DdayEntity> findByToday(@Param("date") LocalDate date);

    List<DdayEntity> findByDateAndMemberId(LocalDate today, Long memberId);

    List<DdayEntity> findAllByDateAfterAndMemberIdOrderByDateAsc(LocalDate date, Long memberId);
}
