package injea.knwremodel.college

import lombok.RequiredArgsConstructor
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/college")
class CollegeController(private val collegeS: CollegeService) {
    @PostMapping("/getMajor")
    fun getMajor(): ResponseEntity<*> {
        return ResponseEntity.ok(collegeS.findAllMajor());
    }
}