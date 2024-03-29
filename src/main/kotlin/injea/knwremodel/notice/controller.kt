package injea.knwremodel.notice

import injea.knwremodel.like.LikeService
import injea.knwremodel.notice.NoticeDTO.*
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/*TODO array size 0 예외처리*/
@RestController
@RequestMapping("/notice")
class NoticeController(private val noticeS: NoticeService, private val likeS: LikeService) {

    class requestPage(
        var major: String?,
        var type: String?,
        var keyword: String?,
        var page: Long,
        var perPage: Long)
    @PostMapping("/requestPage")
    fun requestPage(@RequestBody dto: requestPage): ResponseEntity<*> {
        val major: String = dto.major ?: ""
        val type: String = dto.type ?: ""
        val keyword: String = dto.keyword ?: ""

        val paging = PageRequest.of(dto.page.toInt(), dto.perPage.toInt(), Sort.Direction.DESC, "regdate")
        val notices = noticeS.search(major, type, keyword, paging)
        val result = notices.map { notice ->
            CommonWithoutBody(
                likeS, notice
            )
        }
        return ResponseEntity.ok(result)
    }


    class requestBody(var noticeId: Long)
    @Transactional
    @PostMapping("/requestBody")
    fun requestBody(@RequestBody dto: requestBody): ResponseEntity<*> {
        val notice = noticeS.findById(dto.noticeId)
        return ResponseEntity.ok(Common(likeS, notice))
    }


    class topview(var size: Int)
    @PostMapping("/topView")
    fun getTopView(@RequestBody dto: topview): ResponseEntity<*> {
        val topNotices = noticeS.findTopView(dto.size)
        val result = topNotices.map { notice ->
            CommonWithoutBody(
                likeS, notice
            )
        }
        return ResponseEntity.ok(result)
    }


    class toplike(var major: String?, var size: Int)
    @PostMapping("/toplike")
    fun getTopLikeByMajor(@RequestBody dto: toplike): ResponseEntity<*> {
        val topNotices = noticeS.findTopLike(dto.major, dto.size)
        val result = topNotices.map { notice ->
            CommonWithoutBody(
                likeS, notice
            )
        }
        return ResponseEntity.ok(result)
    }
}