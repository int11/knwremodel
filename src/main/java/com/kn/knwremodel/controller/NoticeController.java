package com.kn.knwremodel.controller;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
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

    @PostMapping("/top3likes/{major}")
    public ResponseEntity getTopLikesByMajor(@PathVariable String major) {
        List<Notice> topNotices = noticeS.findTopLikesByMajor(major);

        topNotices.sort(Comparator.comparing(Notice::getLikeCount).reversed()
                .thenComparing(Notice::getCreateDate, Comparator.reverseOrder()));

        List<NoticeDTO.responsebody> result = topNotices.stream().map(notice -> new NoticeDTO.responsebody(likeS, notice)).collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/top5View")
    public String getTop5View(Model model, @PageableDefault(size = 5, sort = "view", direction = Sort.Direction.DESC) Pageable pageable) {
        List<Notice> topNotices = noticeS.findTop5ByView(pageable);

        topNotices.sort(Comparator.comparing(Notice::getView).reversed()
                .thenComparing(Notice::getCreateDate, Comparator.reverseOrder()));

        model.addAttribute("topNotices", topNotices);
        return "top5View";
    }
}