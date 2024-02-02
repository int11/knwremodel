package injea.knwremodel.user

import injea.knwremodel.entity.Role

class UserDTO {
    class Common(var oAuthServiceId: String, var nameAttributeKey: String, var attributes: Map<String, Any>) {
        var name: String
        var email: String
        var picture: String

        init {
            this.name = this.attributes["name"] as String
            this.email = this.attributes["email"] as String
            this.picture = this.attributes["picture"] as String
//            if (oAuthServiceId == "google") {
//                this.name = this.attributes["name"] as String
//                this.email = this.attributes["email"] as String
//                this.picture = this.attributes["picture"] as String
//            } else if (oAuthServiceId == "naver") {
//                // TODO naver logic
//            } else{
//                // TODO 지원되지않는 oauth 오류
//            }
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
