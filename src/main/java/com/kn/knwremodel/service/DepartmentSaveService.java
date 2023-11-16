package com.kn.knwremodel.service;

import com.kn.knwremodel.dto.UserDTO;
import com.kn.knwremodel.entity.Role;
import com.kn.knwremodel.entity.User;
import com.kn.knwremodel.repository.UserRepository;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DepartmentSaveService {
    private final UserRepository userRepository;
    private final HttpSession httpSession;

    public void updateUserDepartment(String newDepartment) {
        // 세션에서 현재 사용자 정보를 가져옵니다.
        UserDTO.Session userDTO = (UserDTO.Session)httpSession.getAttribute("user");

        // 사용자 이메일을 사용하여 UserRepository에서 사용자를 찾습니다.
        User user = userRepository.findByEmail(userDTO.getEmail()).get();

        // 사용자의 역할을 새로운 역할로 업데이트합니다.
        user.setDepartment(newDepartment);

        // 업데이트된 사용자 정보를 UserRepository를 통해 저장합니다.
        userRepository.save(user);
    }
}