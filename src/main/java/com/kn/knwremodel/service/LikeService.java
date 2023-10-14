package com.kn.knwremodel.service;

import com.kn.knwremodel.dto.LikeDTO;
import com.kn.knwremodel.entity.Like;
import com.kn.knwremodel.entity.Notice;
import com.kn.knwremodel.repository.LikeRepository;
import com.kn.knwremodel.repository.NoticeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LikeService {
    private final LikeRepository likeRepository;
    private final NoticeRepository noticeRepository;

    //User 임의 구성
    @Transactional
    public Long addLike(LikeDTO.Request dto) throws Exception {
        Notice notice = noticeRepository.findById(dto.getNoticeId()).get();
        String loginUser = dto.getUser();

        //사용자가 해당 게시물에 좋아요를 눌렀던 기록이 있다면
        if (likeRepository.existsByUserAndNotice(loginUser, notice)) {
            throw new Exception("add_false");
        }

        Like like = Like.builder()
                .user(loginUser)
                .notice(notice)
                .build();
        likeRepository.save(like);
        notice.updateLikeCount(notice.getLikeCount() + 1);

        return like.getLikeId();
    }
    @Transactional
    public Long deleteLike(LikeDTO.Request dto) throws Exception {

        Notice notice = noticeRepository.findById(dto.getNoticeId()).get();
        String loginUser = dto.getUser();

        //사용자가 해당 게시물에 좋아요를 눌렀던 기록이 없다면
        if (!likeRepository.existsByUserAndNotice(loginUser, notice)) {
            throw new Exception("delete_false");
        }

        Like like = likeRepository.findByUserAndNotice(loginUser, notice);
        likeRepository.delete(like);
        notice.updateLikeCount(notice.getLikeCount() - 1);

        return like.getLikeId();
    }

}
