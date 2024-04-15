package com.example.project.controller;

import com.example.project.dto.Member;
import com.example.project.service.MemberService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MemberController {
    @Autowired
    MemberService memberService;

    @GetMapping(value="/login")
    public String login() {
        return "member/login";
    }

    @PostMapping(value="/login")
    public String loginMember(Member member, HttpSession session) {

        try {
            Member loginMember = memberService.loginMember(member);

            //2. 데이터가 일치하면 (로그인 성공 시) index 페이지로 이동
            if(loginMember != null) {
                session.setAttribute("name", loginMember.getName());
                session.setAttribute("member_id", loginMember.getMemberId());
                return "redirect:/";
            }

        } catch (Exception e) {

            throw new RuntimeException(e);
        }



        return "member/login";
    }


    @GetMapping(value = "/logout")
    public String logoutMember(HttpSession session) {
        session.removeAttribute("name");
        session.removeAttribute("member_id");

        return "redirect:/";
    }
}
