package injea.knwremodel.user

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
class UserController(
    private val userS: UserService
) {
    class setDepartment(var department: String)
    @PostMapping("/setDepartment")
    fun saveDepartment(@RequestBody department: setDepartment): ResponseEntity<*> {
        userS.setCurrentUserDepartment(department.department)
        return ResponseEntity.ok("학부 저장을 성공했습니다.")
    }

    @PostMapping("/request")
    fun request(): ResponseEntity<*> {
        return ResponseEntity.ok(userS.getCurrentUser())
    }

    @PostMapping("/likes")
    fun likedNotice(): ResponseEntity<*> {
        return ResponseEntity.ok(userS.getCurrentUserLikes())
    }
    // TODO 확인필요
    @PostMapping("/comments")
    fun comments(): ResponseEntity<*> {
        return ResponseEntity.ok(userS.getCurrentUserComments())
    }
}
