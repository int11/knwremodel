package injea.knwremodel.like

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/like")
class LikeController(private val likeS: LikeService) {
    class click(val noticeId: Long)
    @PostMapping("/click")
    fun clickLike(@RequestBody dto: click): ResponseEntity<*> {
        likeS.clickLike(dto.noticeId)
        return ResponseEntity.ok(null)
    }
}