//package com.third.gachigong.controller;
//
//import com.third.gachigong.dto.StudytimeDto;
////import com.third.gachigong.entity.Article;
//import com.third.gachigong.entity.MemberEntity;
//import com.third.gachigong.entity.StudytimeEntity;
//import com.third.gachigong.repository.StudytimeRepository;
//import com.third.gachigong.service.MemberService;
//import com.third.gachigong.service.StudytimeService;
//import jakarta.servlet.http.HttpSession;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import java.time.Duration;
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Slf4j
//@Controller
//public class StudytimeController {
//    private final MemberService memberService;
//    private final StudytimeService studytimeService;
//
//    @Autowired
//    private StudytimeRepository studytimeRepository;
//
//    @Autowired
//    public StudytimeController(MemberService memberService, StudytimeService studytimeService) {
//        this.memberService = memberService;
//        this.studytimeService = studytimeService;
//    }
//
//
//    @PostMapping("/studytime/add")
//    public String addSubject(StudytimeDto studytimeDto, Model model, HttpSession session){
//        log.info(studytimeDto.toString());
//
//        String loginId = (String) session.getAttribute("loginId");
//        if (loginId == null) {
//            return "redirect:/login"; // Redirect to the login page or handle it appropriately
//        }
//
//        MemberEntity memberEntity = memberService.findByMemberId(loginId);
//        if (memberEntity == null) {
//            return "redirect:/login"; // Redirect to the login page or handle it appropriately
//        }
//
//        studytimeDto.setMember(memberEntity);
//        studytimeDto.setDate(LocalDate.now());
//
//        StudytimeEntity studytimeEntity = studytimeDto.toEntity();
//        log.info(studytimeEntity.toString());
//
//        StudytimeEntity saved = studytimeRepository.save(studytimeEntity);
//        log.info(saved.toString());
//
//        // DB에서 데이터 가져오기
//        List<StudytimeEntity> studytimeEntities = studytimeRepository.findByMember(memberEntity).stream().filter(s -> LocalDate.now().equals(s.getDate())).collect(Collectors.toList());
//
//
//        model.addAttribute("timeList", studytimeEntities);
//        return "main";
//    }
//
//    @PostMapping("/studytime/upload/{id}")
//    public void uploadStudyTime(@PathVariable Long id, @RequestBody Map<String, String> requestBody, HttpSession session) {
//        // 받아온 값 중 formattedTime을 꺼냄
//        String formattedTime = requestBody.get("formattedTime");
//        log.info(formattedTime);
//
//        // formattedTime을 Duration 객체로 변환
//        Duration studyTime = parseFormattedTime(formattedTime);
//
//        String loginId = (String) session.getAttribute("loginId");
//
//        MemberEntity memberEntity = memberService.findByMemberId(loginId);
//
//        // StudytimeDto에 studyTime 설정
//        StudytimeEntity studytimeEntity = (StudytimeEntity) studytimeRepository.findByMember(memberEntity);
//        studytimeEntity.setStudyTime(studyTime);
//        studytimeRepository.save(studytimeEntity);
//    }
//
//    private Duration parseFormattedTime(String formattedTime) {
//        // 'hh:mm:ss' 형식의 문자열을 Duration 객체로 변환
//        String[] timeComponents = formattedTime.split(":");
//        long seconds = Long.parseLong(timeComponents[0]) * 3600 +
//                Long.parseLong(timeComponents[1]) * 60 +
//                Long.parseLong(timeComponents[2]);
//        return Duration.ofSeconds(seconds);
//    }
//}
