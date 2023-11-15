package com.kn.knwremodel.controller;

import com.kn.knwremodel.dto.UserDTO;
import com.kn.knwremodel.entity.Role;
import com.kn.knwremodel.service.AuthChangeGuestUserService;
import com.kn.knwremodel.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import java.util.Timer;
import java.util.TimerTask;

@Controller
@RequiredArgsConstructor
public class MailController {
    private final MailService mailService;
    private final AuthChangeGuestUserService authChangeGuestUserService;
    private final HttpSession httpSession;

    private Timer expirationTimer; // 인증 번호 만료를 위한 타이머

    // 인증 번호 발송
    @ResponseBody
    @PostMapping("/mail")
    public synchronized String MailSend(String mail) {
        int number = mailService.sendMail(mail);
        long expirationTime = System.currentTimeMillis() + 90 * 1000; // 현재 시간 + 90초
        httpSession.setAttribute("authNumber", new AuthInfo(number, expirationTime));

        // 타이머를 설정하여 1분 30초 후에 인증 번호를 만료시킴
        scheduleExpirationTimer(90 * 1000, number);

        return "인증번호 발송. 1분 30초 안에 입력하시오.";
    }

    // 인증 번호 확인
    @ResponseBody
    @PostMapping("/confirmNumber")
    public synchronized String ConfirmNumber(String enteredNumber) {
        AuthInfo authInfo = (AuthInfo) httpSession.getAttribute("authNumber");

        // 만료 여부를 체크하고 만료된 경우에만 제거
        if (authInfo != null && authInfo.getNumber() == Integer.parseInt(enteredNumber)) {
            if (authInfo.isValid()) {
                authChangeGuestUserService.updateUserRole(Role.USER);

                UserDTO.Session currentuserDTO = (UserDTO.Session)httpSession.getAttribute("user");
                currentuserDTO.setRole("ROLE_USER");
                
                cancelExpirationTimer(); // 인증이 성공하면 타이머를 취소
                httpSession.setAttribute("user", currentuserDTO);
                httpSession.removeAttribute("authNumber");
                return "이메일 인증이 성공했습니다.";
            } else {
                httpSession.removeAttribute("authNumber");
                return "인증 번호가 만료되었습니다. 다시 시도해주세요";
            }
        } else {
            return "인증 번호가 다르거나 만료되었습니다.";
        }
    }

    // 타이머를 설정하여 주어진 시간(delay)이 경과하면 인증 번호를 만료시킴
    private void scheduleExpirationTimer(long delay, int number) {
        expirationTimer = new Timer();
        expirationTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                httpSession.removeAttribute("authNumber");
                expirationTimer.cancel(); // 작업 완료 후 타이머 종료
            }
        }, delay);
    }

    // 타이머를 취소
    private void cancelExpirationTimer() {
        if (expirationTimer != null) {
            expirationTimer.cancel();
        }
    }

    // 인증 번호와 만료 시간을 저장하는 내부 클래스
    private static class AuthInfo {
        private final int number;
        private final long expirationTime;

        public AuthInfo(int number, long expirationTime) {
            this.number = number;
            this.expirationTime = expirationTime;
        }

        public int getNumber() {
            return number;
        }

        public boolean isValid() {
            return System.currentTimeMillis() <= expirationTime;
        }
    }
}