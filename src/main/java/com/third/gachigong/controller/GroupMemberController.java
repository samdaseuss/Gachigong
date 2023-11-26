package com.third.gachigong.controller;

import com.third.gachigong.entity.GroupEntity;
import com.third.gachigong.entity.GroupMemberEntity;
import com.third.gachigong.entity.MemberEntity;
import com.third.gachigong.service.GroupMemberService;
import com.third.gachigong.service.GroupService;
import com.third.gachigong.service.MemberService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class GroupMemberController {

    @Autowired
    private GroupMemberService groupMemberService;

    @Autowired
    private MemberService userService;

    @Autowired
    private GroupService groupService;

    // 그룹 멤버 생성
    @PostMapping("/gmember")
    public String saveGroupMember(@ModelAttribute("groupMember") GroupMemberEntity groupMember, Model model, HttpSession session) {
        long groupId = 1; // 임시값 ( 화면에 어떻게 넣어야 할 지 고민 중 )
        String userId = (String) session.getAttribute("loginId");
        if (userId != null) {
            // 그룹 찾기
            GroupEntity group = groupService.findByGroupId(groupId);
            // 회원 정보 찾기
            MemberEntity user = userService.findByMemberId(userId);
            // 가입 정보 업데이트
            boolean saveGroupMember = groupMemberService.saveGroupMember(user,group,user.getId(),groupId);
            if(saveGroupMember == true) {
                // 가입 성공
                model.addAttribute("success", "성공");
                return "redirect:/group";
            } else {
                // 가입 실패
                model.addAttribute("error", "이미 가입된 멤버입니다.");
                return "redirect:/group";
            }
        } else {
            // 인증 에러
            model.addAttribute("error", "로그인 후 이용해주세요.");
            return "login";
        }
    }
}
