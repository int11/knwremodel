package injea.knwremodel.Notice

import injea.knwremodel.Like.LikeService
import injea.knwremodel.Notice.NoticeDTO.*
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.stream.Collectors

@RestController
@RequestMapping("/notice")
class NoticeController(private val noticeS: NoticeService, private val likeS: LikeService) {
    @PostMapping("/requestPage")
    fun requestPage(@RequestBody dto: requestPage): ResponseEntity<*> {
        val paging = PageRequest.of(dto.page.toInt(), dto.perPage.toInt(), Sort.Direction.DESC, "regdate")
        val notices = noticeS.search(dto.major, dto.type, dto.keyword, paging)
        val result = notices?.map { notice: Notice ->
            responsePage(
                likeS, notice
            )
        }
        return ResponseEntity.ok(result)
    }

    @PostMapping("/requestbody")
    fun requestbody(@RequestBody dto: requestbody): ResponseEntity<*> {
        val notice = noticeS.findById(dto.id)
        return ResponseEntity.ok(responsebody(likeS, notice))
    }

    @PostMapping("/toplike")
    fun getTopLikeByMajor(@RequestBody dto: toplike): ResponseEntity<*> {
        val topNotices = noticeS.findTopLike(dto.major, PageRequest.of(0, dto.size, Sort.Direction.DESC, "likeCount"))
        val result = topNotices?.map { notice: Notice ->
            responsePage(
                likeS, notice
            )
        }
        return ResponseEntity.ok(result)
    }

    @PostMapping("/topView")
    fun getTopView(@RequestBody dto: topview): ResponseEntity<*> {
        val topNotices = noticeS.findTopView(PageRequest.of(0, dto.size, Sort.Direction.DESC, "view"))
        val result = topNotices?.map { notice: Notice ->
            responsePage(
                likeS, notice
            )
        }
        return ResponseEntity.ok(result)
    }
}