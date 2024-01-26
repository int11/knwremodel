package injea.knwremodel.Notice;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import injea.knwremodel.Like.LikeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/notice")
@RequiredArgsConstructor
public class NoticeController {
    private final NoticeService noticeS;
    private final LikeService likeS;
    
    
    @PostMapping("/requestPage")
    public ResponseEntity requestPage(@RequestBody NoticeDTO.requestPage dto) {
        PageRequest paging = PageRequest.of(dto.getPage().intValue(), dto.getPerPage().intValue(),Sort.Direction.DESC, "id");
        Page<Notice> notices = noticeS.search(dto.getMajor(), dto.getType(), dto.getKeyword(), paging);
        Page<NoticeDTO.responsePage>result = notices.map(notice -> new NoticeDTO.responsePage(likeS, notice));
        return ResponseEntity.ok(result);
    }

    @PostMapping("/requestbody")
    public ResponseEntity requestbody(@RequestBody NoticeDTO.requestbody dto) {
        Notice notice = noticeS.findById(dto.getDbid());
        return ResponseEntity.ok(new NoticeDTO.responsebody(likeS, notice));
    }

    @PostMapping("/toplike")
    public ResponseEntity getTopLikeByMajor(@RequestBody NoticeDTO.toplike dto) {
        List<Notice> topNotices = noticeS.findTopLike(dto.getMajor(), PageRequest.of(0, dto.getSize(), Sort.Direction.DESC, "likeCount"));
        List<NoticeDTO.responsebody> result = topNotices.stream().map(notice -> new NoticeDTO.responsebody(likeS, notice)).collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/topView")
    public ResponseEntity getTopView(@RequestBody NoticeDTO.topview dto) {
        List<Notice> topNotices = noticeS.findTopView(PageRequest.of(0, dto.getSize(), Sort.Direction.DESC, "view"));
        List<NoticeDTO.responsebody> result = topNotices.stream().map(notice -> new NoticeDTO.responsebody(likeS, notice)).collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }
}