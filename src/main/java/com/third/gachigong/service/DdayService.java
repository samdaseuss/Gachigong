package com.third.gachigong.service;

import com.third.gachigong.entity.DdayEntity;
import com.third.gachigong.entity.MemberEntity;
import com.third.gachigong.repository.DdayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
public class DdayService {
    @Autowired
    private DdayRepository ddayRepository;

    public void saveDday(DdayEntity ddayEntity, MemberEntity member) {
        ddayEntity.setMember(member);
        this.ddayRepository.save(ddayEntity);
    }

    public void deleteDday(Long id, MemberEntity member) {
        this.ddayRepository.deleteById(id);
    }

    public List<DdayEntity> getDdayByToday(LocalDate localdate) {
        Instant instant = localdate.atStartOfDay(ZoneId.systemDefault()).toInstant();
        Date date = Date.from(instant);
        return this.ddayRepository.findByToday(localdate);
    }

}
