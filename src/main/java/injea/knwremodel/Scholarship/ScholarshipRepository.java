package injea.knwremodel.Scholarship;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScholarshipRepository extends JpaRepository<Scholarship, Long> {
    boolean existsByRegdate(LocalDate localDate);
    List<Scholarship> findTop5ByOrderByIdDesc();
}