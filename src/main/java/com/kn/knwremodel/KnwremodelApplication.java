package com.kn.knwremodel;

import com.kn.knwremodel.service.testdatainsertService;
import com.kn.knwremodel.service.NoticeService;
import com.kn.knwremodel.service.haksaService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Locale;

@SpringBootApplication
@EnableScheduling
@EnableJpaAuditing
@RequiredArgsConstructor
public class KnwremodelApplication {
	private final NoticeService noticeS;
	private final testdatainsertService testdatainsertS;
	private final haksaService haksaS;

	public static void main(String[] args) {
		SpringApplication.run(KnwremodelApplication.class, args);
	}

	@Scheduled(fixedRate = 1000 * 60 * 30 , initialDelay = 0)
	public void testSchedule() throws IOException {
		testdatainsertS.Gentestdata();
		noticeS.updata();
		System.out.println("DataBase Update every 30 mininutes");
		noticeS.setNowDate(LocalDate.now());
	}

	@Scheduled(fixedRate = 1000 * 60 * 30 , initialDelay = 0)// 임의로 넣어둔 것. 수정 필요
	public void updateHaksa() throws IOException {
		haksaS.crawlAndSaveData();
		System.out.println("Haksa Data Updated every 6 months");

		// 만약, 학사 DB가 비어있다면 추가적인 크롤링 - 테이블이 생성 자체만으로도 비어있지 않다고 인식. 수정 필요
		if (haksaS.checkIfHaksaDBIsEmpty()) {
			haksaS.crawlAndSaveData();
			System.out.println("Database is empty, perform additional crawling");
		}
	}
}
