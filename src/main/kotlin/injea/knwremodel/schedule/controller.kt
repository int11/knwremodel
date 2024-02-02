package injea.knwremodel.schedule

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/Haksa")
class ScheduleController(private val haksaS: ScheduleService) {
    @PostMapping("/request")
    fun request(): ResponseEntity<*> {
        return ResponseEntity.ok(haksaS.findAll())
    }
}