package injea.knwremodel.schedule

import org.jsoup.Jsoup
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Service
class HaksaService(private val haksaRepo: repository) {
    fun update() {
        // 학사 데이터가 아예 존재하지않을때 or 크롤링한 학사 일정 년도수가 현재 년도수랑 다를떄
        if (haksaRepo.findAll().size == 0 || findAll()[0].dateStart.year != LocalDate.now().year) {
            haksaRepo.deleteAll()
            val url = "https://web.kangnam.ac.kr/menu/02be162adc07170ec7ee034097d627e9.do"
            val schedules: MutableList<Schedule> = mutableListOf()

            val document = Jsoup.connect(url).get()

            val contents = document.getElementsByClass("cal_data table tleft").select("tbody th, tbody td")
            val dateContents = contents.select("th")
            val textContents = contents.select("td")

            val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")

            for (i in 0 until dateContents.size) {
                // dateContents 형식은 "01.01(월)" or "12.18(월)~01.09(화)" 전자일경우 size 1 후자일 경우 size 2
                var date = dateContents[i].text().split("~")
                val text = textContents[i].text()
                date = date.map {
                    LocalDate.now().year.toString() + "." + it.substring(0, 5)
                }

                val dateStart = LocalDate.parse(date[0], formatter)
                var dateEnd = LocalDate.parse(date[0], formatter)
                if (date.size == 2) dateEnd = LocalDate.parse(date[1], formatter)

                schedules.add(Schedule(dateStart, dateEnd, text))

            }
            haksaRepo.saveAll(schedules)
        }
    }

    fun findAll(): MutableList<Schedule> {
        return haksaRepo.findAll().filterNotNull().toMutableList()
    }
}
