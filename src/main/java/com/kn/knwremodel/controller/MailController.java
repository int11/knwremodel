package com.kn.knwremodel.controller;

import com.kn.knwremodel.dto.UserDTO;
import com.kn.knwremodel.entity.Role;

import com.kn.knwremodel.service.MailService;
import com.kn.knwremodel.service.UserService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
@Controller
@RequiredArgsConstructor
public class MailController {
    private final MailService mailS;

    // 인증 번호 발송
    @ResponseBody
    @PostMapping("/mail")
    public synchronized ResponseEntity MailSend(String mail) {
        try{
            mailS.sendMail(mail);
            return ResponseEntity.ok("인증번호 발송. 1분 30초 안에 입력하시오.");
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 인증 번호 확인
    @ResponseBody
    @PostMapping("/confirmNumber")
    public synchronized ResponseEntity ConfirmNumber(String enteredNumber) {
        try{
            mailS.confirmNumber(enteredNumber);
            return ResponseEntity.ok("이메일 인증이 성공했습니다.");
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}