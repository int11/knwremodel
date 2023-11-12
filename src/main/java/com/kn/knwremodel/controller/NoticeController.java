package com.kn.knwremodel.controller;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.kn.knwremodel.dto.NoticeDTO;
import com.kn.knwremodel.dto.pageDTO;
import com.kn.knwremodel.entity.Notice;
import com.kn.knwremodel.service.LikeService;
import com.kn.knwremodel.service.NoticeService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/notice")
@RestController
public class NoticeController {
    private final NoticeService noticeS;
    private final LikeService likeS;
    
    @PostMapping("/requestPage")
    public ResponseEntity requestPageNotice(@RequestBody NoticeDTO.requestPage dto) {
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

    @GetMapping("/top5likes/{major}")
    public ResponseEntity getTopLikesByMajor(@PathVariable String major) {
        List<Notice> topNotices = noticeS.findTopLikesByMajor(major);

        topNotices.sort(Comparator.comparing(Notice::getLikeCount).reversed()
                .thenComparing(Notice::getCreateDate, Comparator.reverseOrder()));

        List<NoticeDTO.responsePage> result = topNotices.stream().map(notice -> new NoticeDTO.responsePage(likeS, notice)).collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }
}