package injea.knwremodel.college

import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service

@Service
@RequiredArgsConstructor
class CollegeService(private val CollegeRepo: CollegeRepository) {
    fun findAllMajor(): List<String> {
        return CollegeRepo.findAllMajor()
    }

    fun findAll(): MutableList<College> {
        return CollegeRepo.findAll().filterNotNull().toMutableList()
    }
}