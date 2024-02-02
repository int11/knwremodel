package injea.knwremodel.user

import injea.knwremodel.comment.Comment
import injea.knwremodel.entity.Role
import injea.knwremodel.like.Like
import jakarta.servlet.http.HttpSession
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service


// service call by SecurityConfig.java. checking "oauth2Login.userInfoEndpoint.userService(OAuthService)" function
@Service
class UserService(
    private val userRepo: UserRepository,
    private val httpSession: HttpSession
) :
    OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User {
        val delegate: OAuth2UserService<OAuth2UserRequest, OAuth2User> = DefaultOAuth2UserService()
        val oAuth2User = delegate.loadUser(userRequest)

        // OAuth2 서비스 id 구분코드 ( 구글, 카카오, 네이버 )
        val oAuthServiceId = userRequest.clientRegistration.registrationId

        // OAuth2 로그인 진행시 키가 되는 필드 값 (PK) (구글의 기본 코드는 "sub")
        val nameAttributeKey = userRequest.clientRegistration.providerDetails
            .userInfoEndpoint.userNameAttributeName

        // OAuth2UserService
        val dto = UserDTO.Common(oAuthServiceId, nameAttributeKey, oAuth2User.attributes)

        val user = saveOrUpdate(dto)

        // 세션 정보를 저장하는 직렬화된 dto 클래스
        httpSession.setAttribute("user", UserDTO.Session(user))

        return DefaultOAuth2User(
            setOf(SimpleGrantedAuthority(user.role.key)),
            dto.attributes,
            dto.nameAttributeKey
        )
    }

    /* 소셜로그인시 기존 회원이 존재하면 modifiedDate, name, picture 정보만 업데이트. 새로운 유저면 새로운 Entity 생성 */
    private fun saveOrUpdate(dto: UserDTO.Common): User {
        val user = userRepo.findByEmail(dto.email)?.update(dto.name, dto.picture) ?: dto.toEntity()
        return userRepo.save(user)
    }

    fun findById(id: Long): User {
        //findById(id) 는 Optimal<type> 타입 반환 .orElse(null) 함수를 통해 kotlin "type?" 타입으로 변경
        return userRepo.findById(id).orElse(null) ?: throw NullPointerException("현재 로그인된 계정정보를 찾을 수 없습니다.")
    }
    fun getCurrentUserDTO(): UserDTO.Session {
        // "user" Attribute 없으면 null 반환
        return httpSession.getAttribute("user") as UserDTO.Session? ?: throw NullPointerException("현재 세션에 로그인 되어있지 않습니다.")
    }

    fun isLogin(): Boolean{
        return httpSession.getAttribute("user") as UserDTO.Session? != null
    }

    fun getCurrentUser(): User {
        return findById(getCurrentUserDTO().id)
    }

    fun getCurrentUserComments(): MutableList<Comment>{
        return getCurrentUser().comments.toMutableList()
    }

    fun getCurrentUserLikes(): MutableList<Like>{
        return getCurrentUser().likes.toMutableList()
    }

    fun setCurrentUserRole(newRole: Role) {
        val currentuser = getCurrentUser()
        currentuser.role = newRole
        userRepo.save(currentuser)
        httpSession.setAttribute("user", UserDTO.Session(currentuser))
    }

    fun setCurrentUserDepartment(department: String) {
        val currentuser = getCurrentUser()
        currentuser.department = department
        userRepo.save(currentuser)
        httpSession.setAttribute("user", UserDTO.Session(currentuser))
    }
}