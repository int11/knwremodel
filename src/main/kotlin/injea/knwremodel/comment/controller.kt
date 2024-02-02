package injea.knwremodel.comment

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/comments")
class CommentController(private val commentS: CommentService) {
    // TODO javascript JSON 변경
    class save(val noticeId: Long, val text: String)
    @PostMapping("/save")
    fun saveComment(@RequestBody dto: save): ResponseEntity<*> {
        commentS.saveComment(dto.noticeId, dto.text)
        return ResponseEntity.ok(null)
    }

    class modify(val commentId: Long, val text: String)
    @PostMapping("/modify")
    fun modifyComment(@RequestBody dto: modify): ResponseEntity<*> {
        commentS.modifyComment(dto.commentId, dto.text)
        return ResponseEntity.ok(null)
    }

    class delete(val commentId: Long)
    @PostMapping("/delete")
    fun deleteComment(@RequestBody dto: delete): ResponseEntity<*> {
        return ResponseEntity.ok(commentS.deleteComment(dto.commentId))
    }
}