package com.kn.knwremodel;

import com.kn.knwremodel.service.CollegeService;
import com.kn.knwremodel.service.NoticeCrawlerService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;

@SpringBootApplication
@EnableScheduling
@RequiredArgsConstructor
public class KnwremodelApplication {

	private final NoticeCrawlerService noticeCrawlerService;
	private final CollegeService collegeService;
	public static void main(String[] args) {
		SpringApplication.run(KnwremodelApplication.class, args);
	}

	@Scheduled(fixedRate = 1000 * 60 * 30 , initialDelay = 0)
	public void testSchedule() throws IOException {
		collegeService.testdata();
		noticeCrawlerService.updata();
		System.out.println("DataBase Update every 30 mininutes");
	}
}
