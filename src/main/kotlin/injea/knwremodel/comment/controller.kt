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
    class saveDTO(val noticeId: Long, val text: String)
    @PostMapping("/save")
    fun save(@RequestBody dto: saveDTO): ResponseEntity<*> {
        commentS.saveComment(dto.noticeId, dto.text)
        return ResponseEntity.ok(null)
    }

    class modifyDTO(val commentId: Long, val text: String)
    @PostMapping("/modify")
    fun modify(@RequestBody dto: modifyDTO): ResponseEntity<*> {
        commentS.modifyComment(dto.commentId, dto.text)
        return ResponseEntity.ok(null)
    }

    class deleteDTO(val commentId: Long)
    @PostMapping("/delete")
    fun delete(@RequestBody dto: deleteDTO): ResponseEntity<*> {
        commentS.deleteComment(dto.commentId)
        return ResponseEntity.ok(null)
    }

    class getCommentByNoticeIdDTO(val noticeId: Long)
    @PostMapping("/findByNoticeId")
    fun findByNoticeId(@RequestBody dto: getCommentByNoticeIdDTO): ResponseEntity<*> {
        val comments = commentS.findByNoticeId(dto.noticeId).map { comment -> CommentDTO.Common(comment)}
        return ResponseEntity.ok(comments)
    }
}