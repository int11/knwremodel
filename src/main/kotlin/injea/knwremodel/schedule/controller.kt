package injea.knwremodel.schedule

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/schedule")
class ScheduleController(private val haksaS: ScheduleService) {
    @PostMapping("/findAll")
    fun findAll(): ResponseEntity<*> {
        return ResponseEntity.ok(haksaS.findAll())
    }
}