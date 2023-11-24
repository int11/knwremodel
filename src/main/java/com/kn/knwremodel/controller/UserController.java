package com.kn.knwremodel.controller;

import com.kn.knwremodel.service.UserService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userS;

    @PostMapping("/saveDepartment")
    @ResponseBody
    public ResponseEntity saveDepartment(@RequestParam String department) {
        try {
            userS.setDepartment(department);
            return ResponseEntity.ok("학부 저장을 성공했습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("학부 저장을 실패했습니다.");
        }
    }
}
