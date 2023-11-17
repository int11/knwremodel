package com.kn.knwremodel.controller;

import com.kn.knwremodel.dto.UserDTO;
import com.kn.knwremodel.service.UserService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/saveDepartment")
    @ResponseBody
    public String saveDepartment(@RequestParam String department, HttpSession session) {
        try {
            userService.updateUserDepartment(department);

            // 세션에도 부서 정보 저장
            UserDTO.Session currentUserDTO = (UserDTO.Session) session.getAttribute("user");
            if (currentUserDTO != null) {
                currentUserDTO.setDepartment(department);
                session.setAttribute("user", currentUserDTO);
            }

            return "학부 저장을 성공했습니다.";
        } catch (Exception e) {
            e.printStackTrace();
            return "학부 저장을 실패했습니다.";
        }
    }
}
