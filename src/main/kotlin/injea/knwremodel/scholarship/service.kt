package injea.knwremodel.scholarship

import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.DayOfWeek
import java.time.LocalDate

@Service
@RequiredArgsConstructor
class ScholarshipService(private val scholarshipRepo: ScholarshipRepository) {

    @Transactional
    fun update() {
        val today = LocalDate.now()
        val thisMonday = today.with(DayOfWeek.MONDAY)

        if (!scholarshipRepo.existsByRegdate(thisMonday)) {
            for (i in 0..4) {
                scholarshipRepo.save(Scholarship(thisMonday.plusDays(i.toLong())))
            }
        }
    }

    fun findAll(): MutableList<Scholarship> {
        return scholarshipRepo.findAll().filterNotNull().toMutableList()
    }

    fun save(id: Long, columns: List<String>): Long {
        val scholarship = scholarshipRepo.findById(id).orElseThrow { IllegalArgumentException("위키 수정 실패: 해당 셀이 존재하지 않습니다.") }!!

        scholarship.columns = columns.toMutableList()
        scholarshipRepo.save(scholarship)
        return id
    }
}
