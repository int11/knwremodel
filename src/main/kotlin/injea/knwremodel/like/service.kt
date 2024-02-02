package injea.knwremodel.like

import injea.knwremodel.notice.NoticeService
import injea.knwremodel.user.UserService
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class LikeService(
    private val likeRepo: LikeRepository,
    private val noticeS: NoticeService,
    private val userS: UserService,
) {
    @Transactional
    fun clickLike(id: Long){
        val currentuser = userS.getCurrentUser()
        val clickedNotice = noticeS.findById(id)

        val like = likeRepo.findByUserAndNotice(currentuser, clickedNotice)
        //사용자가 해당 게시물에 좋아요를 눌렀던 기록이 있다면
        if (like == null) {
            val temp = Like(currentuser, clickedNotice)
            likeRepo.save(temp)
            clickedNotice.likeCount += 1
        } else {
            likeRepo.delete(like)
            clickedNotice.likeCount -= 1
        }
    }

    @Transactional
    fun checkedLike(id: Long): Boolean {
        if (userS.isLogin() == false)
            return false
        val currentuser = userS.getCurrentUser()
        val clickedNotice = noticeS.findById(id)
        return likeRepo.existsByUserAndNotice(currentuser, clickedNotice)
    }
}
