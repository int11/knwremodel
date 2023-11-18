package com.kn.knwremodel.service;


import com.kn.knwremodel.dto.UserDTO;
import com.kn.knwremodel.entity.Role;
import com.kn.knwremodel.entity.User;
import com.kn.knwremodel.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;


// service call by SecurityConfig.java. checking "oauth2Login.userInfoEndpoint.userService(OAuthService)" function

@Service
@RequiredArgsConstructor
public class UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
 
    private final UserRepository userRepo;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);
 
        // OAuth2 서비스 id 구분코드 ( 구글, 카카오, 네이버 )
        String oAuthServiceId = userRequest.getClientRegistration().getRegistrationId();
 
        // OAuth2 로그인 진행시 키가 되는 필드 값 (PK) (구글의 기본 코드는 "sub")
        String nameAttributeKey = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();
 
        // OAuth2UserService
        UserDTO.Common dto = new UserDTO.Common(oAuthServiceId, nameAttributeKey, oAuth2User.getAttributes());
 
        User user = saveOrUpdate(dto);
        
        // 세션 정보를 저장하는 직렬화된 dto 클래스
        httpSession.setAttribute("user", new UserDTO.Session(user));

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                dto.getAttributes(),
                dto.getNameAttributeKey());
    }
 
    /* 소셜로그인시 기존 회원이 존재하면 수정날짜 정보만 업데이트해 기존의 데이터는 그대로 보존 */

    private User saveOrUpdate(UserDTO.Common dto){
        User user = userRepo.findByEmail(dto.getEmail())
                .map(entity -> entity.update(dto.getName(), dto.getPicture()))
                .orElse(dto.toEntity());

        return userRepo.save(user);
    }

    public void setDepartment(String newDepartment) {
        // 세션에서 현재 사용자 정보를 가져옵니다.
        UserDTO.Session userDTO = (UserDTO.Session)httpSession.getAttribute("user");

        // 사용자 이메일을 사용하여 UserRepository에서 사용자를 찾습니다.
        User user = userRepo.findByEmail(userDTO.getEmail()).get();

        // 사용자의 역할을 새로운 역할로 업데이트합니다.
        user.setDepartment(newDepartment);

        // 업데이트된 사용자 정보를 UserRepository를 통해 저장합니다.
        userRepo.save(user);
        httpSession.setAttribute("user", new UserDTO.Session(user));
    }

    public void setRole(Role newRole) {
        UserDTO.Session userDTO = (UserDTO.Session)httpSession.getAttribute("user");
        User user = userRepo.findByEmail(userDTO.getEmail()).get();
        user.setRole(newRole);
        userRepo.save(user);
        httpSession.setAttribute("user", new UserDTO.Session(user));
    }

    public void setNickname(String nickname) {
        UserDTO.Session userDTO = (UserDTO.Session)httpSession.getAttribute("user");
        User user = userRepo.findByEmail(userDTO.getEmail()).get();
        user.setNickname(nickname);
        userRepo.save(user);
        httpSession.setAttribute("user", new UserDTO.Session(user));
    }
}