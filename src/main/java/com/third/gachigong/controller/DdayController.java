package com.third.gachigong.controller;

import com.third.gachigong.entity.DdayEntity;
import com.third.gachigong.entity.MemberEntity;
import com.third.gachigong.service.DdayService;
import com.third.gachigong.service.MemberService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Controller
public class DdayController {
    @Autowired
    private DdayService ddayService;

    @Autowired
    private MemberService userService;

    @PostMapping("/dday")
    public String saveDday(@ModelAttribute("dday") DdayEntity dday, Model model, HttpSession session){
        String userId = (String) session.getAttribute("loginId");
        if (userId != null) {
            MemberEntity user = userService.findByMemberId(userId);
            ddayService.saveDday(dday,user);
            return "redirect:/calendar";
        } else {
            return "redirect:/login";
        }
    }

    @PostMapping("/dday/{id}")
    public String deleteDday(@PathVariable("id") Long id, HttpSession session ) {
        String userId = (String) session.getAttribute("loginId");
        if (userId != null) {
            MemberEntity user = userService.findByMemberId(userId);
            ddayService.deleteDday(id,user);
            return "redirect:/calendar/" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } else {
            return "login";
        }
    }

}
