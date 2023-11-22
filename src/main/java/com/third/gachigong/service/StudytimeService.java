//package com.third.gachigong.service;
//
//import com.third.gachigong.dto.StudytimeDto;
//import com.third.gachigong.entity.MemberEntity;
//import com.third.gachigong.entity.StudytimeEntity;
//import com.third.gachigong.repository.StudytimeRepository;
//import jakarta.persistence.EntityNotFoundException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//@Service
//public class StudytimeService {
//    private final StudytimeRepository studytimeRepository;
//    private final MemberService memberService;
//
//    @Autowired
//    public StudytimeService(StudytimeRepository studytimeRepository, MemberService memberService){
//        this.studytimeRepository = studytimeRepository;
//        this.memberService = memberService;
//    }
//
//
//    public void startTimer(StudytimeDto studytimeDto) {
//        // 사용자 ID를 기반으로 MemberEntity 조회
//        MemberEntity memberEntity = memberService.findByMemberId(studytimeDto.getMember().getMemberId());
//        studytimeDto.setMember(memberEntity);
//
//        // 시작 버튼을 눌렀을 때의 로직
//        studytimeDto.setStartTime(LocalDateTime.now());
//        // DB에 저장하는 로직
//        studytimeRepository.save(StudytimeEntity.toStudytimeEntity(studytimeDto));
//    }
//
//    public void stopTimer(StudytimeDto studytimeDto) {
//        // 사용자 ID를 기반으로 MemberEntity 조회
//        MemberEntity memberEntity = memberService.findByMemberId(studytimeDto.getMember().getMemberId());
//        studytimeDto.setMember(memberEntity);
//
//        // 정지 버튼을 눌렀을 때의 로직
//        studytimeDto.setEndTime(LocalDateTime.now());
//        // DB에 저장하는 로직
//        studytimeRepository.save(StudytimeEntity.toStudytimeEntity(studytimeDto));
//    }
//
//
//
//
//    public void startStudyTimer(Long memberId, String subject, String color) {
//        MemberEntity member = memberService.findById(memberId);
//        StudytimeEntity studytime = new StudytimeEntity();
//        studytime.setMember(member);
//        studytime.setSubject(subject);
//        studytime.setColor(color);
//        studytime.setStartTime(LocalDateTime.now());
//
//        studytimeRepository.save(studytime);
//    }
//
//    public void stopStudyTimer(Long studytimeId) {
//        StudytimeEntity studytime = studytimeRepository.findById(studytimeId)
//                .orElseThrow(() -> new EntityNotFoundException("Study time not found with id: " + studytimeId));
//
//        studytime.setEndTime(LocalDateTime.now());
//
//        studytimeRepository.save(studytime);
//    }
//
//    public List<StudytimeEntity> getStudyTimesByMember(Long memberId) {
//        MemberEntity member = memberService.findById(memberId);
//        return studytimeRepository.findByMember(member);
//    }
//}
