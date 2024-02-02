package injea.knwremodel.test

import injea.knwremodel.college.CollegeService
import injea.knwremodel.comment.CommentService
import injea.knwremodel.schedule.ScheduleService
import injea.knwremodel.like.LikeService
import injea.knwremodel.notice.NoticeController
import injea.knwremodel.notice.NoticeService
import injea.knwremodel.user.UserDTO
import injea.knwremodel.user.UserService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import jakarta.servlet.http.HttpSession
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import java.io.IOException

@Controller
class testController(
    private val haksaS: ScheduleService,
    private val noticeS: NoticeService,
    private val collegeS: CollegeService,
    private val httpSession: HttpSession,
    private val noticeC: NoticeController,
    private val userS: UserService,
    private val likeS: LikeService,
    private val commentS: CommentService
) {
    @GetMapping(value = ["/"])
    @Throws(IOException::class)
    fun test(
        keyword: String?,
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        model: Model
    ): String {
        val currentuserDTO = httpSession.getAttribute("user") as UserDTO.Session?
        model.addAttribute("currentuser", currentuserDTO)

        model.addAttribute("majorlist", collegeS.findAllMajor())
        return "mainpage"
    }

    @GetMapping("/read/{noticeid}")
    fun findNotice(@PathVariable noticeid: Long?, model: Model?): String {
        return "noticebody"
    }

    @GetMapping(value = ["/haksa"])
    fun test111(model: Model): String {
        val haksas = haksaS.findAll()
        model.addAttribute("test", haksas)

        return "haksa"
    }

    @GetMapping("/top5View")
    fun getTop5View(model: Model): String {
        val topNotices = noticeS.findTopView(5)

        model.addAttribute("topNotices", topNotices)
        return "top5View"
    }

    @GetMapping("/myPage")
    fun showLikedNoticesAndComments(model: Model): String {
        val currentuserDTO = httpSession.getAttribute("user") as UserDTO.Session?
        model.addAttribute("currentuser", currentuserDTO)

        model.addAttribute("likedNotices", userS.getCurrentUserLikes())
        model.addAttribute("comments", userS.getCurrentUserComments())

        return "myPage"
    }

    @GetMapping("/scholarshipwiki")
    fun scholarshipwiki(model: Model?): String {
        return "scholarshipwiki"
    }
}