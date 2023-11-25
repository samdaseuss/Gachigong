package com.third.gachigong.controller;

import com.third.gachigong.service.*;
import com.third.gachigong.entity.*;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class GroupController {

    @Autowired
    private GroupService groupService;

    @Autowired
    private MemberService userService;

    @Autowired
    private GroupMemberService groupMemberService;


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
                        "groupId", (group.getId() != null) ? group.getId() : "",
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
    public void deleteGroup(@PathVariable Long groupId, HttpSession session) {
        String userId = (String) session.getAttribute("loginId");
        if (userId != null) {
            MemberEntity user = userService.findByMemberId(userId);
            groupService.deleteGroup(user.getId(), groupId);
        }
    }

    // 특정 그룹 정보 불러오기
    @GetMapping("/group/{groupId}")
    public String getOneGroup(@PathVariable Long groupId, Model model, HttpSession session) {
        String userId = (String) session.getAttribute("loginId"); // 로그인 유저 정보
        if (userId != null) {
            MemberEntity user = userService.findByMemberId(userId);
            // 시간이 나면 여기에다가 소유자가 클릭하면 바로 세부 그룹 정보 부르는 기능 추가
            GroupEntity group = groupService.findByGroupId(groupId);
            model.addAttribute("groupName", group.getGroupName());
            model.addAttribute("groupIntro", group.getGroupIntro());
            return "onegroup";
        } else {
            System.out.println("로그인 후 이용해주세요.");
            model.addAttribute("error", "로그인 후 이용해주세요.");
            return "login";
        }
    }

    // 그룹 정보 체크 ( 그룹 패스워드 일치하는지 )
    @PostMapping("/group/{groupId}")
    public String validatepw(@PathVariable Long groupId, @ModelAttribute("groupPassword") String groupPassword, RedirectAttributes redirectAttributes, Model model, HttpSession session) {
        String userId = (String) session.getAttribute("loginId"); // 로그인 유저 정보
        if (userId != null) {
            MemberEntity user = userService.findByMemberId(userId);
            groupService.findByGroupPw(groupId,groupPassword);
            redirectAttributes.addFlashAttribute("groupId", groupId);
            return "redirect:/dgroup";
        } else {
            System.out.println("로그인 후 이용해주세요.");
            model.addAttribute("error", "로그인 후 이용해주세요.");
            return "login";
        }
    }

    // 세부 그룹 정보 불러오기
    @GetMapping("/dgroup")
    public String DetailGroup(@ModelAttribute("groupId") long groupId, Model model) {
        System.out.println(groupId);
        GroupEntity group = groupService.findByGroupId(groupId);
        List<Object[]> results = groupMemberService.findMemberDetailsByGroupId(groupId);
        System.out.println(results);
        List<String> resultList = new ArrayList<>();
        for (Object[] result : results) {
            if (result[0] instanceof GroupMemberEntity) {
                GroupMemberEntity groupMember = (GroupMemberEntity) result[0];
                MemberEntity member = groupMember.getMember();
                String memberName = member.getMemberName();
                resultList.add(memberName);
            }
        }
        model.addAttribute("groupName", group.getGroupName());
        model.addAttribute("groupIntro", group.getGroupIntro());
        model.addAttribute("groupMemberName", resultList);
        return "donegroup";
    }



}