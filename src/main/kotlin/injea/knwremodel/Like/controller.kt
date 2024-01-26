package injea.knwremodel.Like

import injea.knwremodel.Like.LikeDTO.click
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/like")
class LikeController(private val likeS: LikeService) {
    @PostMapping("/click")
    @Throws(Exception::class)
    fun clickLike(@RequestBody dto: click): ResponseEntity<*> {
        return try {
            ResponseEntity.ok(likeS.clickLike(dto))
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(e.message)
        }
    }
}