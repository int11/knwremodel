package injea.knwremodel.comment

import injea.knwremodel.comment.CommentDTO.delete
import injea.knwremodel.comment.CommentDTO.modify
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/comments")
class CommentController(private val commentS: CommentService) {
    /* CREATE */
    @PostMapping("/save")
    @Throws(Exception::class)
    fun saveComment(@RequestBody commentdto: CommentDTO.save): ResponseEntity<*> {
        return try {
            ResponseEntity.ok(commentS.saveComment(commentdto))
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(e.message)
        }
    }

    @PostMapping("/modify")
    @Throws(Exception::class)
    fun modifyComment(@RequestBody commentdto: modify): ResponseEntity<*> {
        return try {
            ResponseEntity.ok(commentS.modifyComment(commentdto))
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(e.message)
        }
    }

    @PostMapping("/delete")
    @Throws(Exception::class)
    fun deleteComment(@RequestBody commentdto: delete): ResponseEntity<*> {
        return try {
            ResponseEntity.ok(commentS.deleteComment(commentdto))
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(e.message)
        }
    }
}