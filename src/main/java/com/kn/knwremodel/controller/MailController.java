package com.kn.knwremodel.controller;

import com.kn.knwremodel.entity.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.kn.knwremodel.service.AuthChangeGuestUserService;
import com.kn.knwremodel.service.MailService;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class MailController {
    private final MailService mailService;
    private final AuthChangeGuestUserService authChangeGuestUserService;
    private final HttpSession httpSession;

    @GetMapping("/mailaccess")
    public String MailPage() {
        return "index";
    }

    @ResponseBody
    @PostMapping("/mail")
    public String MailSend(String mail) {
        int number = mailService.sendMail(mail);
        String num = "" + number;
        return num;
    }

    @ResponseBody
    @PostMapping("/updateUserRole")
    public String UpdateUserRole() {
        // 이 부분에서 사용자 역할을 업데이트하는 로직을 수행합니다.
        authChangeGuestUserService.updateUserRole(Role.USER);
        return "사용자 역할이 업데이트되었습니다.";
    }
}
