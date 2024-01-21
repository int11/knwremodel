package injea.knwremodel.College;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/college")
@RequiredArgsConstructor
public class CollegeController {
    private final CollegeService collegeS;

    @PostMapping("/getMajor")
    public ResponseEntity getMajor() throws Exception {
        return ResponseEntity.ok(collegeS.findAllMajor());
    }
}