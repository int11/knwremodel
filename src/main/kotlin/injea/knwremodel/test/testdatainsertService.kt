package injea.knwremodel.test

import injea.knwremodel.college.College
import injea.knwremodel.college.CollegeRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class testdatainsertService(private val CollegeRepo: CollegeRepository) {
    @Transactional
    fun Gentestdata() {
        val Colleges: MutableList<College> = ArrayList()
        // hard coding for test
        Colleges.add(College(1L, "강남대학교", "강남대학교", "https://web.kangnam.ac.kr/menu/f19069e6134f8f8aa7f689a4a675e66f.do"))
        Colleges.add(College(2L, "복지융합대학", "스포츠복지전공", "https://ksps.kangnam.ac.kr/menu/3c5c4cf0bebe416ae09666bee7b38984.do"))
        Colleges.add(College(3L, "복지융합대학", "유니버설아트디자인학과", "https://uad.kangnam.ac.kr/menu/a79de9b3128bc2343d9411dfedb753c7.do"))
        Colleges.add(College(4L, "복지융합대학", "실버산업학과", "https://senior-industry.kangnam.ac.kr/menu/f61730fb09006de23bee07331bfda13e.do"))
        Colleges.add(College(5L, "복지융합대학", "사회복지학부", "https://knusw.kangnam.ac.kr/menu/22dd7f703ec676ffdecdd6b4e4fe1b1b.do"))
        Colleges.add(College(6L, "경영관리대학", "글로벌경영학부", "https://globalbiz.kangnam.ac.kr/menu/6b108ed6447a9b934100e9a1ddc0c070.do"))
        Colleges.add(College(7L, "경영관리대학", "정경학부", "https://pet.kangnam.ac.kr/menu/04cb25b9b9430b879fd82a9ef90206aa.do"))
        Colleges.add(College(8L, "글로벌인재대학", "기독교학과", "https://dcs.kangnam.ac.kr/menu/942f430954b529e6d910d5ba8c61a29f.do"))
        Colleges.add(College(9L, "글로벌인재대학", "글로벌문화학부", "https://kcc.kangnam.ac.kr/menu/29b8da331f6b973f09bfef239630d1b7.do"))
        Colleges.add(College(10L, "글로벌인재대학", "음악학과", "https://musicdpt.kangnam.ac.kr/menu/ac37fa802cc891d4909f48061ae97ecc.do"))
        Colleges.add(College(11L, "공과대학", "ICT융합공학부", "https://sae.kangnam.ac.kr/menu/e408e5e7c9f27b8c0d5eeb9e68528b48.do"))
        Colleges.add(College(12L, "공과대학", "인공지능융합공학부", "https://ace.kangnam.ac.kr/menu/f3a3bfbbc5715e4180657f71177d8bcf.do"))
        Colleges.add(College(13L, "공과대학", "부동산건설학부", "https://knureal.kangnam.ac.kr/menu/26c80ce9728657a14fe638d4c566ead8.do"))
        Colleges.add(College(14L, "사범대학", "교육학과", "https://educ.kangnam.ac.kr/menu/8e4d118b43e9fb0f1d3d8ff4a35911c4.do"))
        Colleges.add(College(15L, "사범대학", "유아교육과", "https://knece.kangnam.ac.kr/menu/1e179ef9d06b26f3ae133a18a5ee1ba7.do"))
        Colleges.add(College(16L, "사범대학", "초등·중등특수교육과", "https://sped.kangnam.ac.kr/menu/f1b0fb76ba66f0a7319fc278bed29175.do"))
        Colleges.add(College(17L, "KUN참인재대학", "KUN참인재대학", "https://clas.kangnam.ac.kr/menu/0a9724422ac736bd5d7eb039deab70db.do"))
        CollegeRepo.saveAll(Colleges)
    }
}