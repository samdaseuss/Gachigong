package com.third.gachigong.controller;

import com.third.gachigong.dto.MemberDto;
import com.third.gachigong.entity.DdayEntity;
import com.third.gachigong.entity.GroupEntity;
import com.third.gachigong.entity.MemberEntity;
import com.third.gachigong.entity.StudytimeEntity;
import com.third.gachigong.repository.DdayRepository;
import com.third.gachigong.repository.StudytimeRepository;
import com.third.gachigong.service.GroupService;
import com.third.gachigong.service.MemberService;
import com.third.gachigong.service.DdayService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.time.temporal.ChronoUnit;

@Slf4j //로깅 기능
@Controller
@RequiredArgsConstructor
public class MemberController {
    @Autowired
    private MemberService memberService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private DdayService ddayService;

    @Autowired
    private StudytimeRepository studytimeRepository;


    @GetMapping("/login")
    public String loginForm(){
        return "login";
    }
    @GetMapping("/signup")
    public String signupForm(){
        return "signup";
    }

    @PostMapping("/member/signup")
    public String save(@ModelAttribute MemberDto memberDto, Model model) {
        log.info(memberDto.toString());
        if (memberService.isMemberIdExists(memberDto.getMemberId())) {
            model.addAttribute("error", "이미 사용 중인 아이디입니다. 다른 아이디를 입력해주세요.");
            return "signup";
        }
        memberService.save(memberDto);
        return "login";
    }

    @PostMapping("/member/login")
    public String login(@ModelAttribute MemberDto memberDto, HttpSession session, Model model){
        log.info(memberDto.toString());
        MemberDto loginResult = memberService.login(memberDto);
        if(loginResult != null){
            //세션 기능
            session.setAttribute("loginId", loginResult.getMemberId());

            String loginId = (String) session.getAttribute("loginId");
            MemberEntity memberEntity = memberService.findByMemberId(loginId);
            List<StudytimeEntity> studytimeEntities = studytimeRepository.findByMember(memberEntity).stream().filter(s -> LocalDate.now().equals(s.getDate())).collect(Collectors.toList());
            List<Map<String, String>> ddayList = ddayService.calculateAndPrintAllDdays(memberEntity.getId());
            System.out.println(ddayList);
            model.addAttribute("ddayList", ddayList);
            model.addAttribute("today", LocalDate.now());
            model.addAttribute("totalTime", addTimes(studytimeEntities));
            model.addAttribute("userGroups", groupService.getGroupsByUserId(memberEntity.getId()));
            model.addAttribute("timeList", studytimeEntities);
            return "main";
        }else {
            return "login";
        }
    }

    public static String addTimes(List<StudytimeEntity> studytimeEntities) {
        ArrayList<String> times = new ArrayList<>();
        for (StudytimeEntity s : studytimeEntities){
            times.add(s.getStudyTime());
        }
        int totalHours = 0;
        int totalMinutes = 0;
        int totalSeconds = 0;

        for (String time : times) {
            String[] parts = time.split(":");
            totalHours += Integer.parseInt(parts[0]);
            totalMinutes += Integer.parseInt(parts[1]);
            totalSeconds += Integer.parseInt(parts[2]);
        }

        totalMinutes += totalSeconds / 60;
        totalSeconds %= 60;

        totalHours += totalMinutes / 60;
        totalMinutes %= 60;

        // Formatting to ensure two digits for each part
        String formattedHours = String.format("%02d", totalHours);
        String formattedMinutes = String.format("%02d", totalMinutes);
        String formattedSeconds = String.format("%02d", totalSeconds);

        return formattedHours + ":" + formattedMinutes + ":" + formattedSeconds;
    }


    @GetMapping("/")
    public String main(Model model, HttpSession session) {
        String userId = (String) session.getAttribute("loginId");

        if (userId != null) {
            MemberEntity user = memberService.findByMemberId(userId);
            List<GroupEntity> userGroups = groupService.getGroupsByUserId(user.getId());
            List<StudytimeEntity> studytimeEntities = studytimeRepository.findByMember(user).stream().filter(s -> LocalDate.now().equals(s.getDate())).collect(Collectors.toList());
            List<DdayEntity> todayDdays = ddayService.getTodayDdaysMain(user.getId());
            List<Map<String, String>> ddayList = ddayService.calculateAndPrintAllDdays(user.getId());
            model.addAttribute("ddayList", ddayList);
            model.addAttribute("todayDdays", todayDdays);
            model.addAttribute("userGroups", groupService.getGroupsByUserId(user.getId()));
            model.addAttribute("timeList", studytimeEntities);
            model.addAttribute("today", LocalDate.now());
            model.addAttribute("totalTime", addTimes(studytimeEntities));
            return "main";
        } else {
            return "login";
        }
    }

    @GetMapping("/member/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "login";
    }
}