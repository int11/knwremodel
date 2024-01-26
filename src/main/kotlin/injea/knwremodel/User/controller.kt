package injea.knwremodel.User

import injea.knwremodel.comment.CommentService
import injea.knwremodel.Like.LikeService
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
    @get:GetMapping("/getDepartment")
    val department: ResponseEntity<*>
        get() {
            try {
                val department = userS.department
                return ResponseEntity.ok(department)
            } catch (e: Exception) {
                e.printStackTrace()
                return ResponseEntity.badRequest().body("학과 정보를 가져오는데 실패했습니다.")
            }
        }

    @PostMapping("/saveDepartment")
    fun saveDepartment(@RequestParam department: String?): ResponseEntity<*> {
        try {
            userS.department = department
            return ResponseEntity.ok("학부 저장을 성공했습니다.")
        } catch (e: Exception) {
            e.printStackTrace()
            return ResponseEntity.badRequest().body("학부 저장을 실패했습니다.")
        }
    }

    @PostMapping("/request")
    fun request(): ResponseEntity<*> {
        try {
            val currentuserDTO = httpSession.getAttribute("user") as UserDTO.Session
            if (currentuserDTO != null) {
                return ResponseEntity.ok(currentuserDTO)
            }
            return ResponseEntity.ok(false)
        } catch (e: Exception) {
            return ResponseEntity.badRequest().body(e.message)
        }
    }

    @PostMapping("/likes")
    @Throws(Exception::class)
    fun likes(): ResponseEntity<*> {
        try {
            val currentuserDTO = httpSession.getAttribute("user") as UserDTO.Session
            if (currentuserDTO != null) {
                return ResponseEntity.ok(likeS.getLikedNotices(currentuserDTO))
            }
            return ResponseEntity.ok(false)
        } catch (e: Exception) {
            return ResponseEntity.badRequest().body(e.message)
        }
    }

    @PostMapping("/comments")
    fun comments(): ResponseEntity<*> {
        try {
            val currentuserDTO = httpSession.getAttribute("user") as UserDTO.Session
            if (currentuserDTO != null) {
                return ResponseEntity.ok(commentS.getCommentsByUser(currentuserDTO.id))
            }
            return ResponseEntity.ok(false)
        } catch (e: Exception) {
            return ResponseEntity.badRequest().body(e.message)
        }
    }

    @get:GetMapping("/getEmail")
    val userEmail: ResponseEntity<*>
        get() {
            try {
                val currentUserDTO = httpSession.getAttribute("user") as UserDTO.Session
                if (currentUserDTO != null) {
                    val userEmail = currentUserDTO.email
                    return ResponseEntity.ok(userEmail)
                }
                return ResponseEntity.ok(false)
            } catch (e: Exception) {
                return ResponseEntity.badRequest().body(e.message)
            }
        }
}
