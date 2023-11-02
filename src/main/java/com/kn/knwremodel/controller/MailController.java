package com.kn.knwremodel.controller;

import com.kn.knwremodel.entity.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.kn.knwremodel.service.AuthChangeGuestUserService;
import com.kn.knwremodel.service.MailService;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;
    private final AuthChangeGuestUserService authChangeGuestUserService; // AuthChangeGuestUserService를 주입합니다.

    @GetMapping("/mailaccess")
    public String MailPage() {
        return "index";
    }

    @ResponseBody
    @PostMapping("/mail")
    public String MailSend(String mail) {
        int number = mailService.sendMail(mail);

        // AuthChangeGuestUserService의 인스턴스를 사용하여 updateUserRole 메서드를 호출합니다.
        authChangeGuestUserService.updateUserRole(mail, Role.USER);

        String num = "" + number;

        return num;
    }
}
