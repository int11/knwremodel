package com.kn.knwremodel;

import com.kn.knwremodel.service.testdatainsertService;
import com.kn.knwremodel.service.NoticeService;
import com.kn.knwremodel.service.HaksaService;
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
	private final HaksaService haksaS;

	public static void main(String[] args) {
		SpringApplication.run(KnwremodelApplication.class, args);
	}

	@Scheduled(fixedRate = 1000 * 60 * 30, initialDelay = 0)
	public void testSchedule() throws IOException {
		noticeS.setNowDate(LocalDate.now());
		testdatainsertS.Gentestdata();
		haksaS.update();
		noticeS.update();
		System.out.println("DataBase Update every 30 mininutes");
	}

	@Scheduled(fixedRate = 1000 * 60 * 60 *24)
	public void updateKeywordRanking() {
		noticeS.resetRanking();
		System.out.println("Keyword Ranking has been reset.");
	}
}
