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
        httpSession.setAttribute("authNumber", number); // 생성된 인증 번호를 세션에 저장
        return "인증번호 발송";
    }

    @ResponseBody
    @PostMapping("/confirmNumber")
    public String ConfirmNumber(String enteredNumber) {
        int generatedNumber = Integer.parseInt(httpSession.getAttribute("authNumber").toString());
        if (generatedNumber == Integer.parseInt(enteredNumber)) {
            authChangeGuestUserService.updateUserRole(Role.USER);
            return "이메일 인증이 성공했습니다.";
        } else {
            return "인증 번호가 다릅니다.";
        }
    }
}
