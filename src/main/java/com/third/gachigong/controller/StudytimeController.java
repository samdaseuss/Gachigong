package com.third.gachigong.controller;

import com.third.gachigong.dto.StudytimeDto;
import com.third.gachigong.entity.MemberEntity;
import com.third.gachigong.entity.StudytimeEntity;
import com.third.gachigong.repository.StudytimeRepository;
import com.third.gachigong.service.DdayService;
import com.third.gachigong.service.GroupService;
import com.third.gachigong.service.MemberService;
import com.third.gachigong.service.StudytimeService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.time.format.DateTimeFormatter;

import static com.third.gachigong.controller.MemberController.addTimes;

@Slf4j
@Controller
public class StudytimeController {
    private final MemberService memberService;
    private final StudytimeService studytimeService;

    @Autowired
    private DdayService ddayService;

    @Autowired
    private StudytimeRepository studytimeRepository;

    @Autowired
    private GroupService groupService;
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
        List<Map<String, String>> ddayList = ddayService.calculateAndPrintAllDdays(memberEntity.getId());
        model.addAttribute("ddayList", ddayList);
        model.addAttribute("today", LocalDate.now());
        model.addAttribute("userGroups", groupService.getGroupsByUserId(memberEntity.getId()));
        model.addAttribute("totalTime", addTimes(studytimeEntities));
        model.addAttribute("timeList", studytimeEntities);
        return "main";
    }

    @ResponseBody
    @PostMapping("/studytime/upload/{id}")
    public void uploadStudyTime(@PathVariable Long id, @Validated StudytimeDto studytimeDto) {
        StudytimeEntity studytimeEntity = studytimeService.findById(id);
        StudytimeEntity temp = StudytimeEntity.toStudytimeEntity(studytimeDto);
        studytimeEntity.setStudyTime(temp.getStudyTime());

        StudytimeEntity saved = studytimeRepository.save(studytimeEntity);
        log.info(saved.toString());
    }

//    @GetMapping("/calendar")
//    public String showNewGroupForm(Model model, HttpSession session) {
//        String userId = (String) session.getAttribute("loginId");
//        if (userId != null) {
//            MemberEntity memberEntity = memberService.findByMemberId(userId);
//            List<StudytimeEntity> studytimeEntities = studytimeRepository.findByMember(memberEntity).stream().filter(s -> LocalDate.now().equals(s.getDate())).collect(Collectors.toList());
//            model.addAttribute("lists", ddayService.getTodayDdays(memberEntity.getId()));
//            model.addAttribute("today", LocalDate.now());
//            model.addAttribute("totalTime", addTimes(studytimeEntities));
//            model.addAttribute("timeList", studytimeEntities);
//            return "calendar";
//        } else {
//            model.addAttribute("error", "로그인 후 이용해주세요.");
//            return "login";
//        }
//    }

    @GetMapping("/calendar/{date}")
    public String showNewCalendar(@PathVariable String date, Model model, HttpSession session) {
        log.info(date + "호출 됨!!");
        String userId = (String) session.getAttribute("loginId");
        if (userId != null) {
            MemberEntity user = memberService.findByMemberId(userId);

            List<StudytimeEntity> studytimeEntities = studytimeRepository.findByMember(user)
                    .stream()
                    .filter(s -> date.equals(s.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))))
                    .collect(Collectors.toList());

            log.info(studytimeEntities.toString());
            model.addAttribute("lists", ddayService.getTodayDdays(date, user.getId()));
            model.addAttribute("today", LocalDate.now());
            model.addAttribute("timeList", studytimeEntities);
            model.addAttribute("totalTime", addTimes(studytimeEntities));

            return "calendar";
        } else {
            model.addAttribute("error", "로그인 후 이용해주세요.");
            return "login";
        }
    }

}