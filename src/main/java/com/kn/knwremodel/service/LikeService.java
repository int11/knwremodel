package com.kn.knwremodel.service;

import com.kn.knwremodel.dto.LikeDTO;
import com.kn.knwremodel.dto.UserDTO;
import com.kn.knwremodel.entity.Like;
import com.kn.knwremodel.entity.Notice;
import com.kn.knwremodel.entity.User;
import com.kn.knwremodel.repository.LikeRepository;
import com.kn.knwremodel.repository.NoticeRepository;
import com.kn.knwremodel.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
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
            return like.getId();
        }
        else{
            Like like = Like.builder()
                            .user(currentuser)
                            .notice(notice)
                            .build();
            likeRepository.save(like);
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
}
