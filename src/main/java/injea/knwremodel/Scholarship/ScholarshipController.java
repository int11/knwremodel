package injea.knwremodel.Scholarship;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/scholarship")
@RequiredArgsConstructor
public class ScholarshipController {
    private final ScholarshipService scholarshipS;

    @PostMapping("/request")
    public synchronized ResponseEntity request() {
        List<Scholarship> scholarships = scholarshipS.findAll();
        List<ScholarshipDTO.Scholarship> result = scholarships.stream().map(scholarship -> new ScholarshipDTO.Scholarship(scholarship)).collect(Collectors.toList());
        Collections.reverse(result);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/save")
    public synchronized ResponseEntity save(@RequestBody ScholarshipDTO.save dto) {
        return ResponseEntity.ok(scholarshipS.save(dto.getScholarshipId(), dto.getColumns()));
    }
}