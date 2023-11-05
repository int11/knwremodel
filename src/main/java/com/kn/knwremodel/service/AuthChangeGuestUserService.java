package com.kn.knwremodel.service;

import com.kn.knwremodel.dto.UserDTO;
import com.kn.knwremodel.entity.Role;
import com.kn.knwremodel.entity.User;
import com.kn.knwremodel.repository.UserRepository;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthChangeGuestUserService {
    private final UserRepository userRepository;
    private final HttpSession httpSession;
    
    public void updateUserRole(Role newRole) {
        UserDTO.Session userDTO = (UserDTO.Session)httpSession.getAttribute("user");
        User user = userRepository.findByEmail(userDTO.getEmail()).get();
        user.setRole(newRole);
        userRepository.save(user);
    }
}
