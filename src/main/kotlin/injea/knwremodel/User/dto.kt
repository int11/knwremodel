package injea.knwremodel.User

import injea.knwremodel.entity.Role
import java.io.Serializable

class UserDTO {
    class Common(oAuthServiceId: String?, nameAttributeKey: String?, attributes: Map<String, Any>?) {
        var oAuthServiceId: String? = null
            private set
        var nameAttributeKey: String? = null
            private set
        var attributes: Map<String, Any>? = null
            private set
        var name: String? = null
            private set
        var email: String? = null
            private set
        var picture: String? = null
            private set

        init {
            if (oAuthServiceId == "google") {
                ofGoogle(oAuthServiceId, nameAttributeKey, attributes)
            } else if (oAuthServiceId == "naver") {
                ofNaver(oAuthServiceId, nameAttributeKey, attributes)
            }
        }

        private fun ofGoogle(oAuthServiceId: String, nameAttributeKey: String?, attributes: Map<String, Any>?) {
            this.oAuthServiceId = oAuthServiceId
            this.nameAttributeKey = nameAttributeKey
            this.attributes = attributes
            this.name = attributes!!["name"] as String?
            this.email = attributes["email"] as String?
            this.picture = attributes["picture"] as String?
        }

        fun toEntity(): User {
            return User.Companion.builder()
                .name(name)
                .email(email)
                .picture(picture)
                .role(Role.GUEST)
                .build()
        }

        class CommonBuilder internal constructor() {
            private var oAuthServiceId: String? = null
            private var nameAttributeKey: String? = null
            private var attributes: Map<String, Any>? = null
            fun oAuthServiceId(oAuthServiceId: String?): CommonBuilder {
                this.oAuthServiceId = oAuthServiceId
                return this
            }

            fun nameAttributeKey(nameAttributeKey: String?): CommonBuilder {
                this.nameAttributeKey = nameAttributeKey
                return this
            }

            fun attributes(attributes: Map<String, Any>?): CommonBuilder {
                this.attributes = attributes
                return this
            }

            fun build(): Common {
                return Common(this.oAuthServiceId, this.nameAttributeKey, this.attributes)
            }

            override fun toString(): String {
                return "UserDTO.Common.CommonBuilder(oAuthServiceId=" + this.oAuthServiceId + ", nameAttributeKey=" + this.nameAttributeKey + ", attributes=" + this.attributes + ")"
            }
        }

        companion object {
            fun builder(): CommonBuilder {
                return CommonBuilder()
            }

            private fun ofNaver(oAuthServiceId: String, nameAttributeKey: String?, attributes: Map<String, Any>?) {
                // TODO naver logic
            }
        }
    }

    class Session(user: User) : Serializable {
        @JvmField
        var id: Long? = user.id
        var name: String? = user.name
        var email: String? = user.email
        var picture: String? = user.picture
        var role: String? = user.roleKey // 역할 정보 추가

        // 변경된 부분
        var department: String? = user.department //부서 정보 추가
    }
}
