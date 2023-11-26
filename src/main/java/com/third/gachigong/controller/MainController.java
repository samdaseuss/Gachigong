package com.third.gachigong.controller;

import com.third.gachigong.entity.GroupEntity;
import com.third.gachigong.entity.MemberEntity;
import com.third.gachigong.service.GroupService;
import com.third.gachigong.service.MemberService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MainController {
    @Autowired
    private MemberService userService;

    @Autowired
    private GroupService groupService;

    @GetMapping("/")
    public String main(Model model, HttpSession session) {
        String userId = (String) session.getAttribute("loginId");
        if (userId != null) {
            MemberEntity user = userService.findByMemberId(userId);
            List<GroupEntity> userGroups = groupService.getGroupsByUserId(user.getId());
            model.addAttribute("userGroups", groupService.getGroupsByUserId(user.getId()));
            return "main";
        } else {
            return "login";
        }
    }

}
