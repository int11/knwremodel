package injea.knwremodel.College;

import java.util.List;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class CollegeService {
    private final CollegeRepository CollegeRepo;
    public List<String> findAllMajor() {
        return CollegeRepo.findAllMajor();
    }
}