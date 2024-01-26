package injea.knwremodel.User

import injea.knwremodel.entity.Role
import jakarta.servlet.http.HttpSession
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.core.OAuth2AuthenticationException
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service


// service call by SecurityConfig.java. checking "oauth2Login.userInfoEndpoint.userService(OAuthService)" function
@Service
class UserService(private val userRepo: UserRepository, private val httpSession: HttpSession) :
    OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    @Throws(OAuth2AuthenticationException::class)
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
            setOf(SimpleGrantedAuthority(user.roleKey)),
            dto.attributes,
            dto.nameAttributeKey
        )
    }

    /* 소셜로그인시 기존 회원이 존재하면 수정날짜 정보만 업데이트해 기존의 데이터는 그대로 보존 */
    private fun saveOrUpdate(dto: UserDTO.Common): User {
        val user = userRepo.findByEmail(dto.email)
            .map { entity: User? -> entity!!.update(dto.name, dto.picture) }
            .orElse(dto.toEntity())

        return userRepo.save(user)
    }

    fun setRole(newRole: Role?) {
        val userDTO = httpSession.getAttribute("user") as UserDTO.Session
        val user = userRepo.findByEmail(userDTO.email).get()
        user.role = newRole
        userRepo.save(user)
        httpSession.setAttribute("user", UserDTO.Session(user))
    }

    var department: String?
        get() {
            val userDTO = httpSession.getAttribute("user") as UserDTO.Session
            return if (userDTO != null) {
                userDTO.department
            } else {
                null
            }
        }
        set(newDepartment) {
            // 세션에서 현재 사용자 정보를 가져옵니다.
            val userDTO = httpSession.getAttribute("user") as UserDTO.Session

            // 사용자 이메일을 사용하여 UserRepository에서 사용자를 찾습니다.
            val user = userRepo.findByEmail(userDTO.email).get()

            // 사용자의 역할을 새로운 역할로 업데이트합니다.
            user.department = newDepartment

            // 업데이트된 사용자 정보를 UserRepository를 통해 저장합니다.
            userRepo.save(user)
            httpSession.setAttribute("user", UserDTO.Session(user))
        }
}