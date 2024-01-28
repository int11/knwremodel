package injea.knwremodel.haksa

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.io.IOException
import java.time.Year
import kotlin.math.min

@Service
class HaksaService {
    @Autowired
    private val haksaRepo: HaksaRepository? = null

    @Autowired
    private val haksayearRepo: HaksayearRepository? = null

    fun update() {
        // 학사 데이터가 아예 존재하지않을때 or 크롤링한 학사 일정 년도수가 현재 년도수랑 다를떄
        if (haksaRepo!!.findAll().size == 0 || haksayearRepo!!.findAll()[0].year == Year.now() == false) {
            haksaRepo.deleteAll()
            haksayearRepo!!.save(Haksayear(1L, Year.now()))
            var docu: Document? = null
            val url = "https://web.kangnam.ac.kr/menu/02be162adc07170ec7ee034097d627e9.do"
            try {
                docu = Jsoup.connect(url).get()
            } catch (e: IOException) {
                e.printStackTrace()
            }

            if (docu != null) {
                val dateElements1 = docu.select("div.tbl.typeA.calendal_list").select("tbody")
                val dateElements = dateElements1.select("th.text-center")
                val scheduleElements = dateElements1.select(".text-center.last")

                val size = min(dateElements.size.toDouble(), scheduleElements.size.toDouble()).toInt()

                for (i in 0 until size) {
                    val date = dateElements[i].text()
                    val schedule = scheduleElements[i].text()
                    val dateParts = date.split("~".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                    val date_start = dateParts[0].trim { it <= ' ' }
                    val date_end = if ((dateParts.size == 2)) dateParts[1].trim { it <= ' ' } else null

                    val event = Haksa()

                    event.schedule = schedule
                    event.setDateStart(date_start)
                    event.setDateEnd(date_end)

                    haksaRepo.save(event)
                }
            }
        }
    }

    fun findAll(): List<Haksa?> {
        return haksaRepo!!.findAll()
    }
}
