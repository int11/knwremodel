package injea.knwremodel.Like;

import injea.knwremodel.Notice.Notice;
import injea.knwremodel.Notice.NoticeRepository;
import injea.knwremodel.User.User;
import injea.knwremodel.User.UserDTO;
import injea.knwremodel.User.UserRepository;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;


@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;
    private final NoticeRepository noticeRepository;
    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Transactional
    public Long clickLike(LikeDTO.click likedto) throws Exception {
        UserDTO.Session currentuserDTO = (UserDTO.Session)httpSession.getAttribute("user");
        Notice notice = noticeRepository.findById(likedto.getNoticeId()).get();
        if (currentuserDTO == null){
            throw new IllegalArgumentException("좋아요 추가 실패: 로그인이 필요합니다.");
        }
        User currentuser = userRepository.findById(currentuserDTO.getId()).get();


        //사용자가 해당 게시물에 좋아요를 눌렀던 기록이 있다면
        if (likeRepository.existsByUserAndNotice(currentuser, notice)) {
            Like like = likeRepository.findByUserAndNotice(currentuser, notice);
            likeRepository.delete(like);
            notice.setLikeCount(notice.getLikeCount() - 1);
            return like.getId();
        }
        else{
            Like like = Like.builder()
                            .user(currentuser)
                            .notice(notice)
                            .build();
            likeRepository.save(like);
            notice.setLikeCount(notice.getLikeCount() + 1);
            return like.getId();
        }
    }

    @Transactional
    public boolean checkedLike(Long noticeid) {
        UserDTO.Session currentuserDTO = (UserDTO.Session)httpSession.getAttribute("user");
        Notice notice = noticeRepository.findById(noticeid).get();
        if (currentuserDTO == null){
            return false;
        }
        User currentuser = userRepository.findById(currentuserDTO.getId()).orElse(null);
        return likeRepository.existsByUserAndNotice(currentuser, notice);
    }

    public List<Notice> getLikedNotices(UserDTO.Session currentUserDTO) {
        Long currentUserId = getCurrentUserId(currentUserDTO);
        if (currentUserId == null) {
            return Collections.emptyList();
        }
        return likeRepository.findLikedNoticesByUser(currentUserId);
    }

    public Long getCurrentUserId(UserDTO.Session currentUserDTO) {

        if (currentUserDTO == null) {
            return null;
        }
        return currentUserDTO.getId();
    }
}
