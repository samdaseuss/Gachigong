package com.third.gachigong.service;
import com.third.gachigong.entity.StudytimeEntity;
import com.third.gachigong.repository.StudytimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


@Service
public class StudytimeService {
    private final StudytimeRepository studytimeRepository;
    private final MemberService memberService;

    @Autowired
    public StudytimeService(StudytimeRepository studytimeRepository, MemberService memberService){
        this.studytimeRepository = studytimeRepository;
        this.memberService = memberService;
    }

    public StudytimeEntity findById(Long Id) {
        return studytimeRepository.findById(Id).orElse(null);
    }

    // 이 부분 추가
    public List<StudytimeEntity> findAllById(Long id) {
        Iterable<java.lang.Long> idIterable = Arrays.asList(id);
        return studytimeRepository.findAllById(idIterable);
    }


}
