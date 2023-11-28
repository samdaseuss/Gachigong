package com.third.gachigong.controller;

import com.third.gachigong.dto.MemberDto;
import com.third.gachigong.entity.MemberEntity;
import com.third.gachigong.entity.StudytimeEntity;
import com.third.gachigong.repository.StudytimeRepository;
import com.third.gachigong.service.GroupService;
import com.third.gachigong.service.MemberService;
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

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j //로깅 기능
@Controller
@RequiredArgsConstructor
public class MemberController {
    @Autowired
    private MemberService memberService;

    @Autowired
    private GroupService groupService;

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

            model.addAttribute("userGroups", groupService.getGroupsByUserId(memberEntity.getId()));
            model.addAttribute("timeList", studytimeEntities);
            return "main";
        }else {
            return "login";
        }
    }

    //추후 그룹 매니저의 추방 기능 참고 (추후 여기서 뺄거임)
    @GetMapping("/root")
    public String findAll(Model model, HttpSession session) {
        String loginId = (String) session.getAttribute("loginId");

        if (loginId != null && loginId.equals("root")) {
            List<MemberDto> memberDtoList = memberService.findAll();
            model.addAttribute("memberList", memberDtoList);
            return "list";
        } else {
            return "main";
        }
    }

    @GetMapping("/member/delete/{id}")
    public String deleteById(@PathVariable Long id){
        memberService.deleteById(id);
        return "redirect:/root";
    }

    @GetMapping("/member/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "login";
    }
}
