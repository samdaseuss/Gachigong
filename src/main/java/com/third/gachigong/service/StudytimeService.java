package com.third.gachigong.service;

import com.third.gachigong.entity.StudytimeEntity;
import com.third.gachigong.repository.StudytimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
