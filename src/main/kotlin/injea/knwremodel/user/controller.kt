package injea.knwremodel.user

import injea.knwremodel.comment.CommentService
import injea.knwremodel.like.LikeService
import jakarta.servlet.http.HttpSession
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
class UserController(
    private val userS: UserService,
    private val httpSession: HttpSession,
    private val commentS: CommentService,
    private val likeS: LikeService
) {
    @PostMapping("/setDepartment")
    fun saveDepartment(@RequestParam department: String?): ResponseEntity<*> {
        userS.department = department
        return ResponseEntity.ok("학부 저장을 성공했습니다.")
    }

    @PostMapping("/request")
    fun request(): ResponseEntity<*> {
        val currentuserDTO = httpSession.getAttribute("user") as UserDTO.Session
        if (currentuserDTO != null) {
            return ResponseEntity.ok(currentuserDTO)
        }
        return ResponseEntity.ok(false)
    }

    @PostMapping("/likes")
    @Throws(Exception::class)
    fun likes(): ResponseEntity<*> {
        val currentuserDTO = httpSession.getAttribute("user") as UserDTO.Session
        if (currentuserDTO != null) {
            return ResponseEntity.ok(likeS.getLikedNotices(currentuserDTO))
        }
        return ResponseEntity.ok(false)
    }

    @PostMapping("/comments")
    fun comments(): ResponseEntity<*> {
        val currentuserDTO = httpSession.getAttribute("user") as UserDTO.Session
        if (currentuserDTO != null) {
            return ResponseEntity.ok(commentS.getCommentsByUser(currentuserDTO.id))
        }
        return ResponseEntity.ok(false)
    }
}
