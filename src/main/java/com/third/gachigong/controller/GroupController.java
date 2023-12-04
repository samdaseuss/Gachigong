package com.third.gachigong.controller;

import com.third.gachigong.repository.StudytimeRepository;
import com.third.gachigong.service.*;
import com.third.gachigong.entity.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static com.third.gachigong.controller.MemberController.addTimes;

@Controller
public class GroupController {

    @Autowired
    private GroupService groupService;

    @Autowired
    private MemberService userService;

    @Autowired
    private GroupMemberService groupMemberService;


    @Autowired
    private StudytimeRepository studytimeRepository;

    @GetMapping("/group")
    public String groupRank(Model model, HttpSession session) {
        String userId = (String) session.getAttribute("loginId");
        if (userId != null) {
            MemberEntity user = userService.findByMemberId(userId);
            List<GroupEntity> myGroups = groupService.findGroupInfoByUserId(user.getId()), // 내가 참여하고 있는 그룹 정보 담김
                    userGroups = groupService.getGroupsByUserId(user.getId()), // 내가 만든 그룹 멤버 정보 담김
                    groupList = groupService.getAllGroups(); // 모든 그룹 정보 담김
            List<Map<String, Object>> groupListData = new ArrayList<>(); // 리스트에 맵 추가
            for (GroupEntity group : groupList) {
                Map<String, Object> groupData = new HashMap<>();
                groupData.put("groupData", Map.of(
                        "groupId", (group.getId() != null) ? group.getId() : "",
                        "groupName", (group.getGroupName() != null) ? group.getGroupName() : "",
                        "groupIntro", (group.getGroupIntro() != null) ? group.getGroupIntro() : ""
                ));
                groupListData.add(groupData); // 가공된 맵 데이터 리스트에 추가
            }
            model.addAttribute("groupListData", groupListData)
                    .addAttribute("userGroups", userGroups)
                    .addAttribute("myGroups", myGroups);
            return "group";
        } else {
            model.addAttribute("error", "로그인 후 이용해주세요.");
            return "login";
        }
    }


    // 내가 만든 그룹은 참여중인 그룹에도 업데이트
    @GetMapping("/newgroup")
    public String showNewGroupForm(Model model, HttpSession session) {
        String userId = (String) session.getAttribute("loginId");
        if (userId != null) {
            GroupEntity group = new GroupEntity();
            model.addAttribute("group", group);
            return "newgroup";
        } else {
            model.addAttribute("error", "로그인 후 이용해주세요.");
            return "login";
        }
    }


    @PostMapping("/group")
    public String saveGroup(@ModelAttribute("group") GroupEntity group, Model model, HttpSession session) {
        String userId = (String) session.getAttribute("loginId");
        if (userId != null) {
            GroupEntity newGroup = groupService.saveGroup(group);
            MemberEntity user = userService.findByMemberId(userId);
            groupService.updateGroupOwner(user.getId(), newGroup.getId());
            boolean saveGroupMember = groupMemberService.saveGroupMember(user,group,user.getId(),newGroup.getId());
            if(saveGroupMember == true) {
                model.addAttribute("success", "성공");
                return "redirect:/group";
            } else {
                model.addAttribute("error", "이미 가입된 멤버입니다.");
                return "redirect:/group";
            }
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
        String userId = (String) session.getAttribute("loginId");
        if (userId != null) {
            MemberEntity user = userService.findByMemberId(userId);
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
    public String validatepw(@PathVariable("groupId") Long groupId, @ModelAttribute("groupPassword") String groupPassword, RedirectAttributes redirectAttributes, Model model, HttpSession session) {
        String userId = (String) session.getAttribute("loginId");
        if (userId != null) {
            MemberEntity user = userService.findByMemberId(userId);
            GroupEntity password = groupService.findByGroupPw(groupId,groupPassword);
            if(password != null) {
                redirectAttributes.addFlashAttribute("groupId", groupId);
                boolean saveGroupMember = groupMemberService.saveGroupMember(user,password,user.getId(),groupId);
                if(saveGroupMember == true) {
                    model.addAttribute("success", "성공");
                    return "redirect:/dgroup";
                } else {
                    model.addAttribute("error", "이미 가입된 멤버입니다.");
                    return "redirect:/dgroup";
                }
            } else {
                model.addAttribute("error", "비밀번호를 입력해주세요.");
                return "redirect:/group";
            }
        } else {
            model.addAttribute("error", "로그인 후 이용해주세요.");
            return "login";
        }
    }


    // 세부 그룹 정보 불러오기
    @GetMapping("/dgroup")
    public String DetailGroup(@ModelAttribute("groupId") long groupId, Model model, HttpSession session) {

        GroupEntity group = groupService.findByGroupId(groupId);
        List<Object[]> results = groupMemberService.findMemberDetailsByGroupId(groupId);
        List<String> resultList = new ArrayList<>();
        for (Object[] result : results) {
            if (result[0] instanceof GroupMemberEntity) {
                GroupMemberEntity groupMember = (GroupMemberEntity) result[0];
                MemberEntity member = groupMember.getMember();
                String memberName = member.getMemberName();
                resultList.add(memberName);
            }
        }
        // 그룹 ID에 해당하는 그룹 정보 불러오기
        GroupEntity groupEntity = groupService.findByGroupId(groupId);
        // 얻은 그룹 엔티티로 해당 그룹에 속한 멤버 정보 불러오기
        List<GroupMemberEntity> groupMemberEntities = groupMemberService.getAllMembersByGroup(groupEntity);
        // 그룹 ID에 해당하는 그룹에 참여하는 멤버 정보 불러오기
        List<Map<String,Object>> studytime = new ArrayList<>();
        for (GroupMemberEntity groupMemberEntity : groupMemberEntities) {
            Map<String, Object> map = new HashMap<>();
            // 그룹 멤버 한명 조회
            MemberEntity member = groupMemberEntity.getMember();
            // 해당 id 정보로 스터디 타임 조회
            List<StudytimeEntity> studytimeEntities = studytimeRepository.findByMember(member).stream().filter(s -> LocalDate.now().equals(s.getDate())).collect(Collectors.toList());
            map.put("studytime", addTimes(studytimeEntities));
            map.put("membername", member.getMemberName());
            studytime.add(map);
        }
        Collections.sort(studytime, (map1, map2) -> {
            LocalTime time1 = LocalTime.parse((CharSequence) map1.get("studytime"), DateTimeFormatter.ofPattern("HH:mm:ss"));
            LocalTime time2 = LocalTime.parse((CharSequence) map2.get("studytime"), DateTimeFormatter.ofPattern("HH:mm:ss"));
            return time2.compareTo(time1);
        });
        model.addAttribute("groupMemberRank", studytime);
        model.addAttribute("groupName", group.getGroupName());
        model.addAttribute("groupIntro", group.getGroupIntro());
        model.addAttribute("groupMemberName", resultList);
        return "donegroup";
    }
}