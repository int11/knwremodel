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
import java.time.Year;
import java.time.YearMonth;
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

	@Scheduled(fixedRate = 1000 * 60 * 30, initialDelay = 0)
	public void testSchedule() throws IOException {
		testdatainsertS.Gentestdata();
		noticeS.updata();
		System.out.println("DataBase Update every 30 mininutes");
		noticeS.setNowDate(LocalDate.now());

		// haksa 크롤링 예외
		Year initialUpdateYear = Year.of(YearMonth.now().getYear());
		Year currentYear = Year.now();

		if (!currentYear.equals(initialUpdateYear)) {
			haksaS.crawlAndSaveData();
			System.out.println("Additional Crawling performed.");
		}
	}
	@Scheduled(fixedDelay = 15768000000L)// 최초 실행 후, 6개월 지나 실행
	public void updateHaksa() throws IOException {
		haksaS.crawlAndSaveData();
		System.out.println("Haksa Data Updated every 6 months.");
	}
}
