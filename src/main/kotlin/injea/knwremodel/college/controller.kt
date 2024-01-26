package injea.knwremodel.college

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/college")
class CollegeController(private val collegeS: CollegeService) {
    @get:Throws(Exception::class)
    @get:PostMapping("/getMajor")
    val major: ResponseEntity<*>
        get() = ResponseEntity.ok(collegeS.findAllMajor())
}