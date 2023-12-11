package com.kn.knwremodel.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kn.knwremodel.dto.NoticeDTO;
import com.kn.knwremodel.dto.pageDTO;
import com.kn.knwremodel.entity.Notice;
import com.kn.knwremodel.service.LikeService;
import com.kn.knwremodel.service.NoticeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/notice")
@RequiredArgsConstructor
public class NoticeController {
    private final NoticeService noticeS;
    private final LikeService likeS;
    
    
    @PostMapping("/requestPage")
    public ResponseEntity requestPage(@RequestBody NoticeDTO.requestPage dto) {
        List<Notice> notices = noticeS.search(dto.getMajor(), dto.getType(), dto.getKeyword());
        List<NoticeDTO.responsePage> result = notices.stream().map(notice -> new NoticeDTO.responsePage(likeS, notice)).collect(Collectors.toList());
        pageDTO<NoticeDTO.responsePage> pagedto = new pageDTO<>(result, dto.getPage(), dto.getPerPage());
        return ResponseEntity.ok(pagedto);
    }

    @PostMapping("/requestbody")
    public ResponseEntity requestbody(@RequestBody NoticeDTO.requestbody dto) {
        Notice notice = noticeS.findById(dto.getDbid());
        return ResponseEntity.ok(new NoticeDTO.responsebody(likeS, notice));
    }

    @PostMapping("/toplike")
    public ResponseEntity getTopLikeByMajor(@RequestBody NoticeDTO.toplike dto) {
        List<Notice> topNotices = noticeS.findTopLike(dto.getMajor(), PageRequest.of(0, dto.getTopsize(), Sort.Direction.DESC, "likeCount"));
        List<NoticeDTO.responsebody> result = topNotices.stream().map(notice -> new NoticeDTO.responsebody(likeS, notice)).collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/topView")
    public ResponseEntity getTopView(@RequestBody NoticeDTO.topview dto) {
        List<Notice> topNotices = noticeS.findTopView(PageRequest.of(0, dto.getTopsize(), Sort.Direction.DESC, "view"));
        List<NoticeDTO.responsebody> result = topNotices.stream().map(notice -> new NoticeDTO.responsebody(likeS, notice)).collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }
}