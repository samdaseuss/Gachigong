package com.example.studytime.controller;

import com.example.studytime.service.*;
import com.example.studytime.entity.*;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpSession;

@Controller
public class GroupController {

    @Autowired
    private GroupService groupService;

    @Autowired
    private UserService userService;

    @GetMapping("/group")
    public String groupRank(Model model, HttpSession session) {

        String userId = (String) session.getAttribute("loginId");

        if (userId != null) {
            Optional<User> userOptional = userService.getUserById(userId);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                model.addAttribute("user", user);
                groupInfo(model, userId);
            }
        }
        return "group";
    }


    @GetMapping("/showNewGroupForm")
    public String showNewGroupForm(Model model) {
        Group group = new Group();
        model.addAttribute("group", group);
        return "new_group";
    }


    @PostMapping("/saveGroup")
    public String saveGroup(@ModelAttribute("group") Group group) {
        groupService.saveGroup(group);
        return "redirect:/group";
    }


    private void groupInfo(Model model, String userId) {
        List<Group> myGroups = groupService.findGroupInfoByUserId(userId);
        List<Group> userGroups = groupService.getGroupsByUserId(userId);
        List<Group> groupList = groupService.getAllGroups();

        model.addAttribute("groupList", groupList);
        model.addAttribute("userGroups", userGroups);
        model.addAttribute("myGroups", myGroups);
    }

}
