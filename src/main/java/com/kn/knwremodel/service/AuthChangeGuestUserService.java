package com.kn.knwremodel.service;

import com.kn.knwremodel.entity.Role;
import com.kn.knwremodel.entity.User;
import com.kn.knwremodel.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthChangeGuestUserService {
    private final UserRepository userRepository;

    public void updateUserRole(String userEmail, Role newRole) {
        Optional<User> optionalUser = userRepository.findByEmail(userEmail);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setRole(newRole);
            userRepository.save(user);
        }
    }
}
