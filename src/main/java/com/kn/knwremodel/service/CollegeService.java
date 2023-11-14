package com.kn.knwremodel.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.kn.knwremodel.repository.CollegeRepository;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class CollegeService {
    private final CollegeRepository CollegeRepo;
    public List<String> findAllMajor() {
        return CollegeRepo.findAllMajor();
    }
}