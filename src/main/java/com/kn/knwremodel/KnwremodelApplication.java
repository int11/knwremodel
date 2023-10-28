package com.kn.knwremodel;

import com.kn.knwremodel.service.CollegeService;
import com.kn.knwremodel.service.NoticeService;
import com.kn.knwremodel.service.haksaService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;

@SpringBootApplication
@EnableScheduling
@EnableJpaAuditing
@RequiredArgsConstructor
public class KnwremodelApplication {
	private final NoticeService noticeS;
	private final CollegeService collegeS;
	private final haksaService haksaS;

	public static void main(String[] args) {
		SpringApplication.run(KnwremodelApplication.class, args);
	}

	@Scheduled(fixedRate = 1000 * 60 * 30 , initialDelay = 0)
	public void testSchedule() throws IOException {
		collegeS.testdata();
		noticeS.updata();
		haksaS.crawlAndSaveData();
		System.out.println("DataBase Update every 30 mininutes");
	}

	@Scheduled(cron = "0 0 1 */6 * ?") // 반기별 1일 0시에 실행
	public void updateHaksa() throws IOException {
		haksaS.crawlAndSaveData();
		System.out.println("Haksa Data Updated every 6 months");
	}
}
