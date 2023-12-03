package com.third.gachigong.service;

import com.third.gachigong.entity.DdayEntity;
import com.third.gachigong.entity.MemberEntity;
import com.third.gachigong.repository.DdayRepository;
import com.third.gachigong.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class DdayService {
    @Autowired
    private DdayRepository ddayRepository;

    @Autowired
    private MemberRepository memberRepository;

    public void saveDday(DdayEntity ddayEntity, MemberEntity member) {
        ddayEntity.setMember(member);
        this.ddayRepository.save(ddayEntity);
    }

    public void deleteDday(Long id, MemberEntity member) {
        boolean isUser = this.memberRepository.existsByMemberId(member.getId().toString());
        this.ddayRepository.deleteById(id);
    }

    public List<DdayEntity> getAllDdays() {
        return ddayRepository.findAll();
    }

    public Long calculateDday(DdayEntity dday) {
        LocalDate dDayDate = dday.getDate();
        LocalDate today = LocalDate.now();
        return ChronoUnit.DAYS.between(today,dDayDate);
    }

    public List<Map<String, String>> calculateAndPrintAllDdays(Long userId) {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        List<DdayEntity> allDdays = ddayRepository.findAllByDateAfterAndMemberIdOrderByDateAsc(yesterday, userId );
        List<Map<String,String>> ddayList = new ArrayList<>();
        for (DdayEntity dday : allDdays) {
            Map<String, String> ddayMap = new HashMap<>();
            String ddayValue = calculateDday(dday).toString();
            String ddayContent = dday.getContent();
            ddayMap.put("ddayValue", ddayValue);
            ddayMap.put("ddayContent", ddayContent);
            ddayList.add(ddayMap);
        }
        return ddayList;
    }


    // 메인 사용
    public List<DdayEntity> getTodayDdaysMain(Long userId) {
        LocalDate dateTime = LocalDate.now();
        return ddayRepository.findByDateAndMemberId(dateTime,userId);
    }

    // 캘린더 사용
    public List<DdayEntity> getTodayDdays( String today, Long userId) {
        String str = today; // 형식 2023-11-28
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateTime = LocalDate.parse(str, formatter);
        return ddayRepository.findByDateAndMemberId(dateTime,userId);
    }

}
