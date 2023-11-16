package com.kn.knwremodel.dto;

import java.io.Serializable;
import java.util.Map;

import com.kn.knwremodel.entity.Role;
import com.kn.knwremodel.entity.User;

import lombok.Builder;
import lombok.Getter;


public class UserDTO {
    @Getter
    public static class Common{
        private String oAuthServiceId;
        private String nameAttributeKey;
        private Map<String, Object> attributes;
        private String name;
        private String email;
        private String picture;

        @Builder
        public Common(String oAuthServiceId, String nameAttributeKey, Map<String, Object> attributes){
            if (oAuthServiceId.equals("google")){
                ofGoogle(oAuthServiceId, nameAttributeKey, attributes);
            }else if(oAuthServiceId.equals("naver")){
                ofNaver(oAuthServiceId, nameAttributeKey, attributes);
            }
        }

        private void ofGoogle(String oAuthServiceId, String nameAttributeKey, Map<String, Object> attributes){
            this.oAuthServiceId = oAuthServiceId;
            this.nameAttributeKey = nameAttributeKey;
            this.attributes = attributes;
            this.name = (String) attributes.get("name");
            this.email = (String) attributes.get("email");
            this.picture = (String) attributes.get("picture");
        }

        private static void ofNaver(String oAuthServiceId, String nameAttributeKey, Map<String, Object> attributes){
            // TODO naver logic
        }

        public User toEntity(){
            return User.builder()
                    .name(name)
                    .email(email)
                    .picture(picture)
                    .role(Role.GUEST)
                    .build();
        }
    }

    @Getter
    public static class Session implements Serializable {

        private Long id;
        private String name;
        private String email;
        private String picture;
        private String role;  // 역할 정보 추가
        private String department; //부서 정보 추가

        public Session(User user) {
            this.id = user.getId();
            this.name = user.getName();
            this.email = user.getEmail();
            this.picture = user.getPicture();
            this.role = user.getRoleKey(); // 변경된 부분
            this.department = user.getDepartment();
        }
        // setRole 메서드 추가
        public void setRole(String role) {
            this.role = role;
        }//세션을 임시로 저장하기 위해 만듬 추후에 삭제할 예정

        public void setDepartment(String department) {
            this.department = department;
        }//세션에 학부를 저장하기 위해 만듬
    }
}
