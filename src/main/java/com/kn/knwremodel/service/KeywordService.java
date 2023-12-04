package com.kn.knwremodel.service;

import com.kn.knwremodel.dto.KeywordDTO;
import com.kn.knwremodel.entity.Keyword;
import com.kn.knwremodel.repository.KeywordRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class KeywordService {
    private final KeywordRepository keywordRepo;


    @Transactional
    public void addRankingKeyword(KeywordDTO.request dto, HttpServletRequest request) { //검섹어 추가, 실검 조회
        String keyword = dto.getKeyword();
        Cookie[] cookies = request.getCookies();

        if (keyword != null) {
            //새로고침 방지
            if (cookies != null)
                for (Cookie cookie : cookies)
                    if (cookie.getName().equals("keyword")) {
                        if (cookie.getValue().contains(keyword))
                            return;
                    }
        }

        if (keyword != null && !keyword.isEmpty()) {
            Keyword onKeyword = keywordRepo.findByKeyword(keyword);
            if (onKeyword == null) {
                Keyword word = Keyword.builder().keyword(keyword).counts(1L).build();
                keywordRepo.save(word);
            } else
                onKeyword.updateKeywordCount(onKeyword.getCounts() + 1);
        }
    }
    @Transactional
    public void resetRanking() { // 랭킹 리셋
        keywordRepo.deleteAllInBatch();
    }

    @Transactional
    public void addRecentKeywords(KeywordDTO.requestRecentlyKeyword dto,
                                          HttpServletRequest request, HttpServletResponse response) {
        String keyword = dto.getKeyword();

        //최근 검색어 추가, 조회
        Cookie oldCookie = null;
        Cookie[] cookies = request.getCookies();

        //키워드라는 이름의 쿠키를 가지고 있다면 oldCookie에 저장
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("keyword")) {
                    oldCookie = cookie;
                }
            }
        }

        // 가지고 있는 쿠키에 키워드가 포함되어 있지 않다면 쿠키값에 키워드를 추가 "_"로 각 키워드 구분
        // 쿠키 값은 1일 동안 유지
        if (keyword != null) {
            if (oldCookie != null) {
                    oldCookie.setValue(oldCookie.getValue() + "_" + keyword);
                    oldCookie.setPath("/");
                    oldCookie.setMaxAge(60 * 60 * 24);
                    response.addCookie(oldCookie);

            } else { // 키워드 쿠키를 가지고 있지 않다면 키워드라는 새로운 쿠키값 생성

                Cookie newCookie = new Cookie("keyword", keyword);
                newCookie.setPath("/");
                newCookie.setMaxAge(60 * 60 * 24);
                response.addCookie(newCookie);
            }
        }
    }

    public List<Keyword> requestFindTop6ByKeyword() {
        return keywordRepo.findTop6ByOrderByCountsDesc();
    }

    public List<String> requestRecentKeywords(HttpServletRequest request) {
        List<String> keywordList = new ArrayList<>(5);
        Cookie[] cookies = request.getCookies();

        // 쿠키 안에 저장되어 있는 쿠키 밸류값을 리스트로 변환
        if (cookies != null)
            for (Cookie cookie : cookies)
                if (cookie.getName().equals("keyword")) {
                    StringTokenizer st = new StringTokenizer(cookie.getValue(), "_");
                    while (st.hasMoreTokens()) {
                        keywordList.add(0, st.nextToken());
                    }
                }

        Long e = Math.min(keywordList.size(), 5L);
        return keywordList.subList(0, e.intValue());
    }
}
