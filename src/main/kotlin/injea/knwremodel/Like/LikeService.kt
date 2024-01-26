package injea.knwremodel.Like

import injea.knwremodel.Like.LikeDTO.click
import injea.knwremodel.User.UserDTO
import injea.knwremodel.User.UserRepository
import injea.knwremodel.notice.Notice
import injea.knwremodel.notice.NoticeRepository
import jakarta.servlet.http.HttpSession
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class LikeService(
    private val likeRepository: LikeRepository,
    private val noticeRepository: NoticeRepository,
    private val userRepository: UserRepository,
    private val httpSession: HttpSession
) {
    @Transactional
    @Throws(Exception::class)
    fun clickLike(likedto: click): Long? {
        val currentuserDTO = httpSession.getAttribute("user") as UserDTO.Session
        val notice = noticeRepository.findById(likedto.noticeId).get()
        requireNotNull(currentuserDTO) { "좋아요 추가 실패: 로그인이 필요합니다." }
        val currentuser = userRepository.findById(currentuserDTO.id).get()


        //사용자가 해당 게시물에 좋아요를 눌렀던 기록이 있다면
        if (likeRepository.existsByUserAndNotice(currentuser, notice)) {
            val like = likeRepository.findByUserAndNotice(currentuser, notice)
            likeRepository.delete(like)
            notice.likeCount = notice.likeCount - 1
            return like.id
        } else {
            val like: Like = Like.Companion.builder()
                .user(currentuser)
                .notice(notice)
                .build()
            likeRepository.save(like)
            notice.likeCount = notice.likeCount + 1
            return like.id
        }
    }

    @Transactional
    fun checkedLike(noticeid: Long): Boolean {
        val currentuserDTO = httpSession.getAttribute("user") as UserDTO.Session
        val notice = noticeRepository.findById(noticeid).get()
        if (currentuserDTO == null) {
            return false
        }
        val currentuser = userRepository.findById(currentuserDTO.id).orElse(null)!!
        return likeRepository.existsByUserAndNotice(currentuser, notice)
    }

    fun getLikedNotices(currentUserDTO: UserDTO.Session?): List<Notice?>? {
        val currentUserId = getCurrentUserId(currentUserDTO)
            ?: return emptyList<Notice>()
        return likeRepository.findLikedNoticesByUser(currentUserId)
    }

    fun getCurrentUserId(currentUserDTO: UserDTO.Session?): Long? {
        if (currentUserDTO == null) {
            return null
        }
        return currentUserDTO.id
    }
}
