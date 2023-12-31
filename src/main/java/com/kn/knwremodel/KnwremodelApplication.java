package com.kn.knwremodel;

import com.kn.knwremodel.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;
import java.time.LocalDate;

@SpringBootApplication
@EnableScheduling
@EnableJpaAuditing
@RequiredArgsConstructor
public class KnwremodelApplication {
	private final NoticeService noticeS;
	private final testdatainsertService testdatainsertS;
	private final HaksaService haksaS;
	private final KeywordService keywordS;
	private final EventService eventS;
	
	public static void main(String[] args) {
		SpringApplication.run(KnwremodelApplication.class, args);
	}

	@Scheduled(fixedRate = 1000 * 60 * 30, initialDelay = 0)
	public void testSchedule() throws Exception {
		testdatainsertS.Gentestdata();

		haksaS.update();
		noticeS.update();
		eventS.update();
		
		System.out.println("DataBase Update every 30 mininutes");
	}

	// 하루
	@Scheduled(fixedRate = 1000 * 60 * 60 *24)
	public void updateKeywordRanking() {
		keywordS.resetRanking();
		System.out.println("Keyword Ranking has been reset.");
	}
}
