package injea.knwremodel

import injea.knwremodel.Haksa.HaksaService
import injea.knwremodel.Notice.NoticeService
import injea.knwremodel.Scholarship.ScholarshipService
import injea.knwremodel.test.testdatainsertService
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled

@SpringBootApplication
@EnableScheduling
@EnableJpaAuditing
class KnwremodelApplication(
    private val noticeS: NoticeService,
    private val testdatainsertS: testdatainsertService,
    private val haksaS: HaksaService,
    private val scholarshipS: ScholarshipService
) {
    @Scheduled(fixedRate = 1000 * 60 * 30, initialDelay = 0)
    @Throws(Exception::class)
    fun testSchedule() {
        testdatainsertS.Gentestdata()

        scholarshipS.update()
        haksaS.update()
        noticeS.updateAll()

        println("DataBase Update every 30 mininutes")
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(KnwremodelApplication::class.java, *args)
        }
    }
}
