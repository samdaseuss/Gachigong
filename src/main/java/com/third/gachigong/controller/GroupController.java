package com.third.gachigong.controller;

import com.third.gachigong.service.*;
import com.third.gachigong.entity.*;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
            List<GroupEntity> myGroups = groupService.findGroupInfoByUserId(user.getId()),
                    userGroups = groupService.getGroupsByUserId(user.getId()),
                    groupList = groupService.getAllGroups();

            List<Map<String, Object>> groupListData = new ArrayList<>();

            for (GroupEntity group : groupList) {
                Map<String, Object> groupData = new HashMap<>();
                groupData.put("groupData", Map.of(
                        "groupName", (group.getGroupName() != null) ? group.getGroupName() : "",
                        "groupIntro", (group.getGroupIntro() != null) ? group.getGroupIntro() : ""
                ));
                groupListData.add(groupData);
            }
            // 클라이언트로 전달
            model.addAttribute("groupListData", groupListData)
                    .addAttribute("userGroups", groupService.getGroupsByUserId(user.getId()))
                    .addAttribute("myGroups", groupService.findGroupInfoByUserId(user.getId()));
            return "group";
        } else {
            model.addAttribute("error", "로그인 후 이용해주세요.");
            return "login";
        }
    }


    @GetMapping("/newgroup")
    public String showNewGroupForm(Model model) {
        GroupEntity group = new GroupEntity();
        model.addAttribute("group", group);
        return "newgroup";
    }


    @PostMapping("/group")
    public String saveGroup(@ModelAttribute("group") GroupEntity group, Model model, HttpSession session) {
        String userId = (String) session.getAttribute("loginId");
        if (userId != null) {
            GroupEntity newGroup = groupService.saveGroup(group);
            MemberEntity user = userService.findByMemberId(userId);
            groupService.updateGroupOwner(user.getId(), newGroup.getId());
            return "redirect:/group";
        } else {
            model.addAttribute("error", "로그인 후 이용해주세요.");
            return "login";
        }
    }

    @DeleteMapping("/group/{groupId}")
    public void deleteGroup(@PathVariable Long groupId,HttpSession session) {
        String userId = (String) session.getAttribute("loginId");
        if (userId != null) {
            MemberEntity user = userService.findByMemberId(userId);
            groupService.deleteGroup(user.getId(), groupId);
        }
    }
}
