package com.kn.knwremodel.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kn.knwremodel.entity.Scholarship;
import com.kn.knwremodel.repository.ScholarshipRepository;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class ScholarshipService {
    private final ScholarshipRepository scholarshipRepo;

    @Transactional
    public void update() {
        LocalDate today = LocalDate.now();
        LocalDate thisMonday = today.with(DayOfWeek.MONDAY);

        if (!scholarshipRepo.existsByRegdate(thisMonday)){

            for(int i = 0; i < 5; i++){
                scholarshipRepo.save(new Scholarship(thisMonday.plusDays(i)));
            }
        }
    }

    public List<Scholarship> findAll() {
        Pageable pageable = PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC,  "id"));
        return scholarshipRepo.findAll(pageable).getContent();
    }

    public Long save(Long id, List<String> columns) {
        Scholarship scholarship = scholarshipRepo.findById(id).orElseThrow(() ->
            new IllegalArgumentException("위키 수정 실패: 해당 셀이 존재하지 않습니다."));

        scholarship.setColumns(columns);
        scholarshipRepo.save(scholarship);
        return id;
    }
}
