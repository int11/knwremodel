package injea.knwremodel.like

import injea.knwremodel.user.UserDTO
import injea.knwremodel.user.UserRepository
import injea.knwremodel.notice.Notice
import injea.knwremodel.notice.NoticeService
import jakarta.servlet.http.HttpSession
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class LikeService(
    private val likeRepo: LikeRepository,
    private val noticeS: NoticeService,
    private val userRepo: UserRepository,
    private val httpSession: HttpSession
) {
    @Transactional
    fun clickLike(id: Long): Long? {
        val currentuserDTO = httpSession.getAttribute("user") as UserDTO.Session
        val notice = noticeS.findById(id)
        requireNotNull(currentuserDTO) { "좋아요 추가 실패: 로그인이 필요합니다." }
        val currentuser = userRepo.findById(currentuserDTO.id).get()


        //사용자가 해당 게시물에 좋아요를 눌렀던 기록이 있다면
        if (likeRepo.existsByUserAndNotice(currentuser, notice)) {
            val like = likeRepo.findByUserAndNotice(currentuser, notice)
            likeRepo.delete(like)
            notice.likeCount = notice.likeCount - 1
            return like.id
        } else {
            val like: Like = Like.Companion.builder()
                .user(currentuser)
                .notice(notice)
                .build()
            likeRepo.save(like)
            notice.likeCount = notice.likeCount + 1
            return like.id
        }
    }

    @Transactional
    fun checkedLike(noticeid: Long): Boolean {
        val currentuserDTO = httpSession.getAttribute("user") as UserDTO.Session
        val notice = noticeS.findById(noticeid)
        if (currentuserDTO == null) {
            return false
        }
        val currentuser = userRepo.findById(currentuserDTO.id).orElse(null)!!
        return likeRepo.existsByUserAndNotice(currentuser, notice)
    }

    fun getLikedNotices(currentUserDTO: UserDTO.Session?): List<Notice?>? {
        val currentUserId = getCurrentUserId(currentUserDTO)
            ?: return emptyList<Notice>()
        return likeRepo.findLikedNoticesByUser(currentUserId)
    }

    fun getCurrentUserId(currentUserDTO: UserDTO.Session?): Long? {
        if (currentUserDTO == null) {
            return null
        }
        return currentUserDTO.id
    }

    fun findById(id: Long): Like? {
        return likeRepo.findById(id).orElse(null)
    }
}
