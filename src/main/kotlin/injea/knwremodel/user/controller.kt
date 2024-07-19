package injea.knwremodel.user

import injea.knwremodel.comment.CommentDTO
import injea.knwremodel.like.Like
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
class UserController(
    private val userS: UserService
) {
    class setDepartmentDTO(var department: String)
    @PostMapping("/setDepartment")
    fun setDepartment(@RequestBody department: setDepartmentDTO): ResponseEntity<*> {
        userS.setCurrentUserDepartment(department.department)
        return ResponseEntity.ok("학부 저장을 성공했습니다.")
    }

    @PostMapping("/getCurrentUser")
    fun getCurrentUser(): ResponseEntity<*> {
        if (userS.isLogin()){
            return ResponseEntity.ok(userS.getCurrentUserDTO())
        }
        return ResponseEntity.ok(false)
    }

    @Transactional
    @PostMapping("/likes")
    fun likedNotice(): ResponseEntity<*> {
        val likes = userS.getCurrentUserLikes()
        class resultDTO(like: Like){
            val id = like.id
            val title = like.notice.title
        }
        val result = likes.map { like -> resultDTO(like) }
        return ResponseEntity.ok(result)
    }

    @Transactional
    @PostMapping("/comments")
    fun comments(): ResponseEntity<*> {
        val result = userS.getCurrentUserComments().map { comment -> CommentDTO.Common(comment) }
        return ResponseEntity.ok(result)
    }
}
