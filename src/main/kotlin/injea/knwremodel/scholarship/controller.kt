package injea.knwremodel.scholarship

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/scholarship")
class ScholarshipController(private val scholarshipS: ScholarshipService) {

    @PostMapping("/request")
    fun request(): ResponseEntity<*> {
        val scholarships = scholarshipS.findAll()
        val result = scholarships.map { scholarship: Scholarship -> ScholarshipDTO.Common(scholarship) }
        return ResponseEntity.ok(result.reversed())
    }

    class save(var scholarshipId: Long, var columns: List<String>)
    @PostMapping("/save")
    fun save(@RequestBody dto: save): ResponseEntity<*> {
        return ResponseEntity.ok(scholarshipS.save(dto.scholarshipId, dto.columns))
    }
}
