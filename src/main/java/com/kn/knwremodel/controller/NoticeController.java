package com.kn.knwremodel.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.kn.knwremodel.dto.NoticeDTO;
import com.kn.knwremodel.entity.Notice;
import com.kn.knwremodel.service.NoticeService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/notice")
@RestController
public class NoticeController {
    private final NoticeService noticeS;

    @PostMapping("/requestPage")
    public ResponseEntity requestPageNotice(@RequestBody NoticeDTO.requestPage noticedto) {
        List<Notice> notices = noticeS.findByMajorAndType(noticedto.getMajor(), noticedto.getType(), noticedto.getCount(), noticedto.getPage());
        List<NoticeDTO.responsePage> result = notices.stream().map(NoticeDTO.responsePage::new).collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/requestbody")
    public ResponseEntity requestbody(@RequestBody NoticeDTO.requestbody noticedto) {
        Notice notice = noticeS.findById(noticedto.getDbid());
        return ResponseEntity.ok(new NoticeDTO.responsebody(notice));
    }

    @GetMapping("/top5likes/{major}")
    public ResponseEntity getTopLikesByMajor(@PathVariable String major) {
        List<Notice> topNotices = noticeS.findTopLikesByMajor(major);
        List<NoticeDTO.responsePage> result = topNotices.stream().map(NoticeDTO.responsePage::new).collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }
}