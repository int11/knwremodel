package injea.knwremodel.user

import injea.knwremodel.entity.Role
import java.io.Serializable

class UserDTO {
    class Common(var oAuthServiceId: String, var nameAttributeKey: String, var attributes: Map<String, Any>) {
        lateinit var name: String
        lateinit var email: String
        lateinit var picture: String

        init {
            if (oAuthServiceId == "google") {
                name = this.attributes["name"] as String
                email = this.attributes["email"] as String
                picture = this.attributes["picture"] as String
            } else if (oAuthServiceId == "naver") {
                // TODO naver logic
            } else{
                // TODO 지원되지않는 oauth 오류
            }
        }

        fun toEntity(): User {
            // TODO Role.GUEST 반환값 체크
            return User(name, email, picture, Role.GUEST)
        }
    }

    class Session(user: User){
        var id = user.id!!
        var name = user.name
        var email = user.email
        var picture = user.picture
        var role = user.role.key
        var department = user.department
    }
}
