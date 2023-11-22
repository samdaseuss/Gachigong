package com.third.gachigong.controller;

import com.third.gachigong.service.*;
import com.third.gachigong.entity.*;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.servlet.http.HttpSession;

@Controller
public class GroupController {

    @Autowired
    private GroupService groupService;

    @Autowired
    private MemberService userService;


    @GetMapping("/group")
    public String groupRank(Model model, HttpSession session) {
        String userId = (String) session.getAttribute("loginId");
        if (userId != null) {
            MemberEntity user = userService.findByMemberId(userId);
            List<GroupEntity>
                    myGroups = groupService.findGroupInfoByUserId(user.getId()),
                    userGroups = groupService.getGroupsByUserId(user.getId()),
                    groupList = groupService.getAllGroups();
            model.addAttribute("groupList", groupService.getAllGroups())
                    .addAttribute("userGroups", groupService.getGroupsByUserId(user.getId()))
                    .addAttribute("myGroups", groupService.findGroupInfoByUserId(user.getId()));
            return "group";
        } else {
            model.addAttribute("error", "로그인 후 이용해주세요.");
            return "login";
        }
    }


    @GetMapping("/newGroup")
    public String showNewGroupForm(Model model) {
        GroupEntity group = new GroupEntity();
        model.addAttribute("group", group);
        return "new_group";
    }


    @PostMapping("/group")
    public String saveGroup(@ModelAttribute("group") GroupEntity group) {
        groupService.saveGroup(group);
        return "redirect:/group";
    }
}
