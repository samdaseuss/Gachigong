package com.third.gachigong.controller;

import com.third.gachigong.dto.StudytimeDto;
import com.third.gachigong.entity.MemberEntity;
import com.third.gachigong.entity.StudytimeEntity;
import com.third.gachigong.repository.StudytimeRepository;
import com.third.gachigong.service.MemberService;
import com.third.gachigong.service.StudytimeService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
public class StudytimeController {
    private final MemberService memberService;
    private final StudytimeService studytimeService;

    @Autowired
    private StudytimeRepository studytimeRepository;

    @Autowired
    public StudytimeController(MemberService memberService, StudytimeService studytimeService) {
        this.memberService = memberService;
        this.studytimeService = studytimeService;
    }


    @PostMapping("/studytime/add")
    public String addSubject(StudytimeDto studytimeDto, Model model, HttpSession session){
        log.info("add 호출");
        log.info(studytimeDto.toString());

        String loginId = (String) session.getAttribute("loginId");
        if (loginId == null) {
            return "redirect:/login"; // Redirect to the login page or handle it appropriately
        }

        MemberEntity memberEntity = memberService.findByMemberId(loginId);
        if (memberEntity == null) {
            return "redirect:/login"; // Redirect to the login page or handle it appropriately
        }

        studytimeDto.setMember(memberEntity);
        studytimeDto.setDate(LocalDate.now());

        StudytimeEntity studytimeEntity = studytimeDto.toEntity();
        studytimeEntity.setStudyTime("00:00:00");
        log.info(studytimeEntity.toString());

        StudytimeEntity saved = studytimeRepository.save(studytimeEntity);
        log.info(saved.toString());

        // DB에서 데이터 가져오기
        List<StudytimeEntity> studytimeEntities = studytimeRepository.findByMember(memberEntity).stream().filter(s -> LocalDate.now().equals(s.getDate())).collect(Collectors.toList());


        model.addAttribute("timeList", studytimeEntities);
        return "main";
    }

    @ResponseBody
    @PostMapping("/studytime/upload/{id}")
    public void uploadStudyTime(@PathVariable Long id, @Validated StudytimeDto studytimeDto) {
        log.info("upload 호출");
        log.info(studytimeDto.toString());

        StudytimeEntity studytimeEntity = studytimeService.findById(id);
        StudytimeEntity temp = StudytimeEntity.toStudytimeEntity(studytimeDto);
        studytimeEntity.setStudyTime(temp.getStudyTime());
        log.info(studytimeEntity.toString());

        StudytimeEntity saved = studytimeRepository.save(studytimeEntity);
        log.info(saved.toString());
    }

    @GetMapping("/calendar")
    public String showNewGroupForm(Model model, HttpSession session) {
        String userId = (String) session.getAttribute("loginId");
        if (userId != null) {
            return "calendar";
        } else {
            model.addAttribute("error", "로그인 후 이용해주세요.");
            return "login";
        }
    }

}
