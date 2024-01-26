package injea.knwremodel.Haksa

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/Haksa")
class HaksaController(private val haksaS: HaksaService) {
    @PostMapping("/request")
    fun request(): ResponseEntity<*> {
        return ResponseEntity.ok(haksaS.findAll())
    }
}